package com.yologger.presentation.screen.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentHomeBinding
import com.yologger.presentation.screen.main.home.register_post.RegisterPostActivity
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
            Logger.w("state: ${it}")
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
            startActivity(intent)
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
    }

    fun moveToTop() = binding.recyclerView.smoothScrollToPosition(0)

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {}
    }
}