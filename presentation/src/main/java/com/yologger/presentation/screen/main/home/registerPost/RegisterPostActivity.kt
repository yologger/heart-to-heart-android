package com.yologger.presentation.screen.main.home.registerPost

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.provider.Settings
import android.view.View
import android.webkit.MimeTypeMap
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
        binding.toolbar.setNavigationIcon(R.drawable.icon_close_outlined_black_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.toolbar.inflateMenu(R.menu.menu_activity_register_post_toolbar)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menu_activity_register_post_toolbar_action_done -> {
                    viewModel.post()
                    true
                }
                else -> {
                    false
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
                is RegisterPostViewModel.Event.Success -> {
                    val createdPost = it.post
                    val intent = Intent()
                    intent.putExtra("created_post", createdPost)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
                is RegisterPostViewModel.Event.Failure -> {
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
                        RegisterPostViewModel.Error.FILE_SIZE_LIMIT_EXCEEDED -> {
                            showToast("10MB 이하의 사진만 업로드할 수 있습니다.")
                            navigateToLogin()
                        }
                        else -> navigateToLogin()
                    }
                }
            }
        }

        viewModel.liveContent.observe(this) {
            val button = binding.toolbar.menu.findItem(R.id.menu_activity_register_post_toolbar_action_done)
            button.isEnabled = !it.isNullOrBlank()
        }
    }

    fun onOpenGalleryButtonClicked(view: View) {
        TedImagePicker.with(this@RegisterPostActivity)
            .image()
            .startMultiImage { imageUris ->
                // 파일 개수 검증
                if (viewModel.getCurrentImagesCount() + imageUris.size > 7) {
                    val snackbar = Snackbar.make(binding.rootView, "이미지는 최대 7장 업로드할 수 있습니다.", Snackbar.LENGTH_SHORT)
                    snackbar.show()
                } else {
                    // 파일 크기 검증
                    val validSizeImageUris = arrayListOf<Uri>()
                    imageUris.forEach { uri ->
                        contentResolver.query(uri, null, null, null, null)?.let { cursor ->
                            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                            cursor.moveToFirst()
                            val size = cursor.getLong(sizeIndex)
                            if (size < 10000000) {
                                validSizeImageUris.add(uri)
                            }
                        }
                    }

                    // 파일 타입 검증
                    if (validSizeImageUris.size != imageUris.size) {
                        val snackbar = Snackbar.make(binding.rootView, "10MB 이상의 파일은 업로드할 수 없습니다.", Snackbar.LENGTH_SHORT)
                        snackbar.show()
                        val mimeTypeMap = MimeTypeMap.getSingleton()
                        val extensions = listOf("png", "jpg")
                        val mimeTypeList = extensions.map { extension ->
                            mimeTypeMap.getMimeTypeFromExtension(extension)
                        }
                        val validTypeImageUris = validSizeImageUris.filter {
                            mimeTypeList.contains(contentResolver.getType(it))
                        }
                        if (validTypeImageUris.size != validSizeImageUris.size) {
                            viewModel.addImageUris(validTypeImageUris)
                        } else {
                            viewModel.addImageUris(validSizeImageUris)
                        }
                    } else {
                        val mimeTypeMap = MimeTypeMap.getSingleton()
                        val extensions = listOf("png", "jpg")
                        val mimeTypeList = extensions.map { extension ->
                            mimeTypeMap.getMimeTypeFromExtension(extension)
                        }
                        val validTypeImageUris = validSizeImageUris.filter {
                            mimeTypeList.contains(contentResolver.getType(it))
                        }
                        if (validTypeImageUris.size != validSizeImageUris.size) {
                            val snackbar = Snackbar.make(binding.rootView, "지원하지 않는 사진 포맷입니다. PNG, JPG 이미지를 사용해주세요.", Snackbar.LENGTH_SHORT)
                            snackbar.show()
                            viewModel.addImageUris(validTypeImageUris)
                        } else {
                            viewModel.addImageUris(validSizeImageUris)
                        }
                    }
                }
            }
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