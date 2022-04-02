package com.yologger.presentation.screen.main.more.showMyPosts

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityShowMyPostsBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowMyPostsActivity : AppCompatActivity() {

    private val viewModel: ShowMyPostsViewModel by viewModels()
    private lateinit var binding: ActivityShowMyPostsBinding
    private lateinit var recyclerViewAdapter: MyPostsRVAdapter

    private val onDeleted = { postId: Long ->
        val builder = AlertDialog.Builder(this@ShowMyPostsActivity)
        val alertDialog = builder
            .setMessage("이 게시글을 삭제하시겠어요?")
            .setPositiveButton("네, 삭제할래요.") { _, _ ->
                viewModel.deletePost(postId)
            }
            .setNegativeButton("취소") { _, _ -> }
            .create()
        alertDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_my_posts)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_my_posts)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_black_24_outlined_arrow_back)
        binding.toolbar.setNavigationOnClickListener { finish() }

        recyclerViewAdapter = MyPostsRVAdapter(context = this@ShowMyPostsActivity, onDeleted = onDeleted)
        binding.recyclerView.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this@ShowMyPostsActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(MyPostsInfiniteScrollListener(layoutManager, viewModel))

        val decoration = DividerItemDecoration(this@ShowMyPostsActivity, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(decoration)

        binding.floatingActionButton.setOnClickListener { moveToTop() }
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(this) {
            when (it) {
                is ShowMyPostsViewModel.State.GetPostsSuccess -> {}
                is ShowMyPostsViewModel.State.GetPostsFailure -> {
                    when (it.error) {
                        ShowMyPostsViewModel.GetPostsError.CLIENT_ERROR -> showToast("Client Error")
                        ShowMyPostsViewModel.GetPostsError.NETWORK_ERROR -> showToast("Network Error")
                        ShowMyPostsViewModel.GetPostsError.JSON_PARSE_ERROR -> showToast("Json Parsing Error")
                        ShowMyPostsViewModel.GetPostsError.NO_SESSION -> {
                            val intent = Intent(this@ShowMyPostsActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        ShowMyPostsViewModel.GetPostsError.NO_POSTS_EXIST -> recyclerViewAdapter.updatePosts(mutableListOf())
                    }
                }
                is ShowMyPostsViewModel.State.DeletePostFailure -> {
                    when (it.error) {
                        ShowMyPostsViewModel.DeletePostError.CLIENT_ERROR, ShowMyPostsViewModel.DeletePostError.NO_POST_EXIST-> showToast("Client Error")
                        ShowMyPostsViewModel.DeletePostError.NETWORK_ERROR -> showToast("Network Error")
                        ShowMyPostsViewModel.DeletePostError.JSON_PARSE_ERROR -> showToast("Json Parsing Error")
                        ShowMyPostsViewModel.DeletePostError.NO_SESSION -> {
                            val intent = Intent(this@ShowMyPostsActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        viewModel.livePosts.observe(this) {
            recyclerViewAdapter.updatePosts(it)
        }

        viewModel.liveIsLoading.observe(this) { isLoading ->
            if (isLoading) {
                recyclerViewAdapter.showLoadingView()
            } else {
                recyclerViewAdapter.hideLoadingView()
            }
        }
    }

    fun moveToTop() = binding.recyclerView.smoothScrollToPosition(0)
}