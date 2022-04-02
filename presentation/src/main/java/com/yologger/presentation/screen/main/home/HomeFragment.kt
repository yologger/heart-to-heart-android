package com.yologger.presentation.screen.main.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yologger.domain.usecase.post.getAllPosts.PostData
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentHomeBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.main.home.registerPost.RegisterPostActivity
import com.yologger.presentation.screen.main.home.userDetail.UserDetailActivity
import com.yologger.presentation.screen.main.home.userDetail.reportUser.ReportUserActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewAdapter: PostsRVAdapter

    private val startRegisterPostActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (it.data?.extras?.getSerializable("created_post") as PostData).let {
                viewModel.addPost(it)
            }
        }
    }

    private val startUserDetailActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }

    private val startReportUserActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

    }

    private val onUserInfoClicked = { memberId: Long ->
        val intent = Intent(requireContext(), UserDetailActivity::class.java)
        intent.putExtra("member_id", memberId)
        startUserDetailActivity.launch(intent)
    }

    private val onReported = { memberId: Long ->
        val intent = Intent(requireContext(), ReportUserActivity::class.java)
        intent.putExtra("member_id", memberId)
        startReportUserActivity.launch(intent)
    }

    private val onBlocked = { memberId: Long ->
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder
            .setTitle("이 사용자 차단하기")
            .setMessage("이 사용자의 모든 게시글을 보지 않으시겠습니까?")
            .setPositiveButton("네, 안볼래요.") { _, _ ->
                viewModel.blockMember(memberId)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
        alertDialog.show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initUI()
    }
    
    private fun observeViewModel() {
        viewModel.liveState.observe(viewLifecycleOwner) {
            when(it) {
                is HomeViewModel.State.GetPostsSuccess -> {}
                is HomeViewModel.State.GetPostsFailure -> {
                    when(it.error) {
                        HomeViewModel.GetPostsError.CLIENT_ERROR -> showToast("Client Error")
                        HomeViewModel.GetPostsError.NETWORK_ERROR -> showToast("Network Error")
                        HomeViewModel.GetPostsError.JSON_PARSE_ERROR -> showToast("Json Parsing Error")
                        HomeViewModel.GetPostsError.NO_SESSION -> {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        HomeViewModel.GetPostsError.NO_POSTS_EXIST -> recyclerViewAdapter.updatePosts(mutableListOf())
                    }
                }
                is HomeViewModel.State.BlockMemberSuccess -> {
                    showToast("이 사용자의 글이 더 이상 보이지 않아요.")
                    viewModel.reloadData()
                }
                is HomeViewModel.State.BlockMemberFailure -> {
                    when(it.error) {
                        HomeViewModel.BlockMemberError.ALREADY_BLOCKING -> showToast("이미 차단 중입니다.")
                        HomeViewModel.BlockMemberError.INVALID_MEMBER_ID -> showToast("Invalid Member ID")
                        HomeViewModel.BlockMemberError.CLIENT_ERROR -> showToast("Client Error")
                        HomeViewModel.BlockMemberError.INVALID_PARAMS -> showToast("Invalid Params")
                        HomeViewModel.BlockMemberError.JSON_PARSE_ERROR -> showToast("Json Parse Error")
                        HomeViewModel.BlockMemberError.NETWORK_ERROR -> showToast("Network Error")
                        HomeViewModel.BlockMemberError.NO_SESSION -> {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        viewModel.livePosts.observe(viewLifecycleOwner) {
            recyclerViewAdapter.updatePosts(it)
        }

        viewModel.liveIsLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                recyclerViewAdapter.showLoadingView()
            } else {
                recyclerViewAdapter.hideLoadingView()
            }
        }
    }

    private fun initUI() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), RegisterPostActivity::class.java)
            startRegisterPostActivity.launch(intent)
        }

        binding.toolbar.inflateMenu(R.menu.menu_fragment_home_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_fragment_home_toolbar_action_refresh -> {
                    viewModel.reloadData()
                    true
                }
            }
            false
        }

        recyclerViewAdapter = PostsRVAdapter(context = requireContext(), onUserInfoClicked = onUserInfoClicked, onReported = onReported, onBlocked = onBlocked)
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(PostsInfiniteScrollListener(layoutManager, viewModel))

        val decoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(decoration)
    }

    fun moveToTop() = binding.recyclerView.smoothScrollToPosition(0)

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }
}