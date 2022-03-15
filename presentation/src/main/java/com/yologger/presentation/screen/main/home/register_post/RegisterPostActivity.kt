package com.yologger.presentation.screen.main.home.register_post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.yologger.presentation.R
import com.yologger.presentation.component.LoadingDialog
import com.yologger.presentation.databinding.ActivityRegisterPostBinding
import com.yologger.presentation.util.navigateToLogin
import com.yologger.presentation.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import gun0912.tedimagepicker.builder.TedImagePicker

@AndroidEntryPoint
class RegisterPostActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSIONS = 1
    }

    private val viewModel: RegisterPostViewModel by viewModels()
    private lateinit var binding: ActivityRegisterPostBinding
    private lateinit var recyclerViewAdapter: SelectedImagesRVAdapter
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initUI()
        observeViewModel()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_post)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    private fun initUI() {
        binding.toolbar.setNavigationIcon(R.drawable.icon_arrow_back_filled_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.activity_register_post_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.activity_register_post_menu_toolbar_action_done -> {
                    viewModel.post()
                    // finish()
                    true
                }
            }
            false
        }

        recyclerViewAdapter = SelectedImagesRVAdapter(context = this@RegisterPostActivity)
        binding.recyclerViewSelectedImages.adapter = recyclerViewAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewSelectedImages.layoutManager = layoutManager
    }

    private fun observeViewModel() {
        viewModel.liveIsLoading.observe(this) {
            if (it) {
                loadingDialog = LoadingDialog(this)
                loadingDialog?.show("Loading")
            } else {
                loadingDialog?.dismiss()
                loadingDialog = null
            }
        }

        viewModel.liveImageUris.observe(this) { imageUris ->
            recyclerViewAdapter.updateImageUris(imageUris)
        }

        viewModel.liveEvent.observe(this) {
            when(it) {
                is RegisterPostViewModel.Event.SUCCESS -> {
                    finish()
                }
                is RegisterPostViewModel.Event.FAILURE -> {
                    when(it.error) {
                        RegisterPostViewModel.Error.CLIENT_ERROR -> {
                            showToast("Client Error")
                            navigateToLogin()
                        }
                        RegisterPostViewModel.Error.NETWORK_ERROR -> {
                            showToast("Network Error")
                            navigateToLogin()
                        }
                        RegisterPostViewModel.Error.FILE_UPLOAD_ERROR -> {
                            showToast("File Upload Error")
                            navigateToLogin()
                        }
                        RegisterPostViewModel.Error.NO_SESSION -> {
                            navigateToLogin()
                        }
                    }
                }
            }
        }
    }

    fun onOpenGalleryButtonClicked(view: View) {
        if (isCameraPermissionGranted()) openGallery()
    }

    private fun isCameraPermissionGranted(): Boolean {
        val requiredPermissions = arrayOf<String>(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val rejectedPermissions = arrayListOf<String>()
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                rejectedPermissions.add(permission)
            }
        }
        if (rejectedPermissions.isNotEmpty()) {
            val array = arrayOfNulls<String>(rejectedPermissions.size)
            // Show request permission dialog.
            ActivityCompat.requestPermissions(this, rejectedPermissions.toArray(array), REQUEST_CODE_PERMISSIONS)
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    for ((index, permission) in permissions.withIndex()) {
                        if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                            // In case permission granted.
                        } else {
                            // In case Permission rejected.
                            AlertDialog.Builder(this@RegisterPostActivity)
                                .setMessage("접근권한이 필요합니다. \n [설정]에서 저장공간을 승인하세요.")
                                .setPositiveButton("설정 열기") { _, _ ->
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS); intent.data = Uri.parse("package:${packageName}")
                                    startActivity(intent)
                                }
                                .setNegativeButton("취소") { _, _ -> }
                                .create()
                                .show()
                            return
                        }
                    }
                    openGallery()
                }
            }
            else -> {
            }
        }
    }

    private fun openGallery() {
        TedImagePicker.with(this@RegisterPostActivity)
            .startMultiImage { imageUris ->
                viewModel.addImageUris(imageUris)
            }
    }
}