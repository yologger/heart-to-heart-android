package com.yologger.presentation.screen.main.more

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentMoreBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.main.more.settings.SettingsActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private val viewModel: MoreViewModel by viewModels<MoreViewModel>()
    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        observeViewModel()
    }

    private fun initUI() {
        binding.toolbar.inflateMenu(R.menu.fragment_more_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.fragment_more_menu_toolbar_action_settings -> {
                    val intent = Intent(requireContext(), SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            false
        }
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(viewLifecycleOwner) {
            when (it) {
                is MoreViewModel.State.SUCCESS -> {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    showToast("Logged out")
                    requireActivity().finish()
                }
                is MoreViewModel.State.FAILURE -> {
                    when(it.error) {
                        MoreViewModel.Error.NETWORK_ERROR -> showToast("Network Error")
                        MoreViewModel.Error.CLIENT_ERROR -> showToast("Client Error")
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = MoreFragment().apply {}
    }
}