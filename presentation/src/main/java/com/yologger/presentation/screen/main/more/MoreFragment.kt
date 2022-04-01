package com.yologger.presentation.screen.main.more

import android.content.Intent
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentMoreBinding
import com.yologger.presentation.screen.auth.login.LoginActivity
import com.yologger.presentation.screen.main.more.follow.FollowActivity
import com.yologger.presentation.screen.main.more.settings.SettingsActivity
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

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
        binding.toolbar.inflateMenu(R.menu.menu_fragment_more_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_fragment_more_toolbar_action_settings -> {
                    val intent = Intent(requireContext(), SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            false
        }
        
        binding.imageViewAvatar.setOnClickListener { 
            TedImagePicker.with(requireContext())
                .image()
                .start { uri ->
                    requireActivity().contentResolver.query(uri, null, null, null, null)?.let { cursor ->
                        val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                        cursor.moveToFirst()
                        val size = cursor.getLong(sizeIndex)
                        if (size > 10000000) {
                            showToast("10MB 이상의 파일은 업로드할 수 없습니다.")
                        } else {
                            val mimeTypeMap = MimeTypeMap.getSingleton()
                            val extensions = listOf("png", "jpg")
                            val mimeTypeList = extensions.map { extension ->
                                mimeTypeMap.getMimeTypeFromExtension(extension)
                            }
                            if (!mimeTypeList.contains(requireActivity().contentResolver.getType(uri))) {
                                showToast("PNG, JPG 이미지만 업로드할 수 있습니다.")
                            } else {
                                viewModel.updateAvatar(uri)
                            }
                        }
                    }
                }
        }

        binding.buttonPost.setOnClickListener {
            // show my posts
        }
        binding.buttonFollower.setOnClickListener {
            val intent = Intent(requireContext(), FollowActivity::class.java)
            startActivity(intent)
        }
        binding.buttonFollowing.setOnClickListener {
            val intent = Intent(requireContext(), FollowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        viewModel.liveState.observe(viewLifecycleOwner) {
            when (it) {
                is MoreViewModel.State.FetchMemberInfoSuccess -> {
                    binding.textViewEmail.text = it.email
                    binding.textViewNickname.text = it.nickname
                    binding.textViewPostValue.text = it.postSize.toString()
                    binding.textViewFollowerValue.text = it.followerSize.toString()
                    binding.textViewFollowingValue.text = it.followingSize.toString()
                    it.avatarUrl?.let { imageUrl ->
                        Glide.with(this@MoreFragment)
                            .load(imageUrl)
                            .centerCrop()
                            .into(binding.imageViewAvatar)
                    }
                }
                is MoreViewModel.State.FetchMemberInfoFailure -> {
                    when(it.error) {
                        MoreViewModel.FetchMemberInfoError.JSON_PARSE_ERROR -> showToast("Json Parse Error")
                        MoreViewModel.FetchMemberInfoError.NETWORK_ERROR -> showToast("Network Error")
                        MoreViewModel.FetchMemberInfoError.CLIENT_ERROR -> showToast("Client Error")
                        MoreViewModel.FetchMemberInfoError.NO_SESSION -> {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        MoreViewModel.FetchMemberInfoError.INVALID_PARAMS -> showToast("Invalid Parameter")
                    }
                }
                is MoreViewModel.State.UpdateAvatarSuccess -> {
                    Glide.with(this)
                        .load(it.imageUrl)
                        .centerCrop()
                        .into(binding.imageViewAvatar)
                }
                is MoreViewModel.State.UpdateAvatarFailure -> {
                    when(it.error) {
                        MoreViewModel.UpdateAvatarError.NETWORK_ERROR -> showToast("Network Error")
                        MoreViewModel.UpdateAvatarError.CLIENT_ERROR, MoreViewModel.UpdateAvatarError.INVALID_PARAMS, MoreViewModel.UpdateAvatarError.JSON_PARSE_ERROR, MoreViewModel.UpdateAvatarError.JSON_PARSE_ERROR -> showToast("Client Error")
                        MoreViewModel.UpdateAvatarError.NO_SESSION -> {
                            val intent = Intent(requireContext(), LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        MoreViewModel.UpdateAvatarError.FILE_UPLOAD_ERROR -> showToast("구글 메일 시스템 에러")
                        MoreViewModel.UpdateAvatarError.IO_ERROR-> showToast("입출력 에러")
                        MoreViewModel.UpdateAvatarError.INVALID_CONTENT_TYPE -> showToast("JPG, PNG 이미지만 업로드 가능합니다.")
                        MoreViewModel.UpdateAvatarError.MEMBER_NOT_EXIST -> showToast("Member Not Exist Error.")
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = MoreFragment().apply {}
    }
}