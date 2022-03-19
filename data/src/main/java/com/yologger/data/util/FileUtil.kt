package com.yologger.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FileUtil (
    private val context: Context
) {
    @SuppressLint("Range")
    fun getMultipart(key: String, uri: Uri): MultipartBody.Part? {
        return context.contentResolver.query(uri, null, null, null, null)?.let {
            if (it.moveToNext()) {
                val displayName = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                val byteArray = context.contentResolver.openInputStream(uri)!!.readBytes()
                val imageRequestBody = RequestBody.create(MediaType.parse("image/*"), byteArray)
                return MultipartBody.Part.createFormData(key, displayName, imageRequestBody)
            } else {
                it.close()
                null
            }
        }
    }
}