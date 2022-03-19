package com.yologger.presentation.screen.main.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yologger.domain.usecase.post.get_posts.PostData
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentHomeBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.main.home.register_post.RegisterPostActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerViewAdapter: PostsRVAdapter

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
                is HomeViewModel.State.Success -> {}
                is HomeViewModel.State.Failure -> {
                    when(it.error) {
                        HomeViewModel.Error.CLIENT_ERROR -> showToast("Client Error")
                        HomeViewModel.Error.NETWORK_ERROR -> showToast("Network Error")
                        HomeViewModel.Error.JSON_PARSE_ERROR -> showToast("Json Parsing Error")
                        HomeViewModel.Error.NO_SESSION -> {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        HomeViewModel.Error.NO_POST_EXIST -> recyclerViewAdapter.updatePosts(mutableListOf())
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

    private val startRegisterPostActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            (it.data?.extras?.getSerializable("created_post") as PostData).let {
                viewModel.addPost(it)
            }
        }
    }

    private fun initUI() {
        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), RegisterPostActivity::class.java)
            startRegisterPostActivity.launch(intent)
        }

        binding.toolbar.inflateMenu(R.menu.fragment_home_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fragment_home_menu_toolbar_action_refresh -> {
                    viewModel.reloadData()
                    true
                }
            }
            false
        }

        recyclerViewAdapter = PostsRVAdapter(requireContext())
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(InfiniteScrollListener(layoutManager, viewModel))

        val decoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
        binding.recyclerView.addItemDecoration(decoration)
    }

    fun moveToTop() = binding.recyclerView.smoothScrollToPosition(0)

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }
}