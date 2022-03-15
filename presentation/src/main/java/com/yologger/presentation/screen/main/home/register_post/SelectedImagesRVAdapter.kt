package com.yologger.presentation.screen.main.home.register_post

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yologger.presentation.R

class SelectedImagesRVAdapter constructor(
    private var imageUris: List<Uri> = mutableListOf(),
    private val context: Context
) : RecyclerView.Adapter<SelectedImagesRVAdapter.ItemHolder>() {

    inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.activity_create_holder_selected_image_imageView)
        fun bind(uri: Uri) {
            Glide.with(context)
                .load(uri)
                .transform(CenterCrop(), RoundedCorners(16))
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.activity_register_post_holder_selected_image, parent, false)
        return ItemHolder(rootView)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(imageUris[position])
    }

    override fun getItemCount(): Int = imageUris.size

    fun updateImageUris(imageUris: List<Uri>) {
        this.imageUris = imageUris
        notifyDataSetChanged()
    }
}