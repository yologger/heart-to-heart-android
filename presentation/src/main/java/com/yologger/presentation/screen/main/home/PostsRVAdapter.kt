package com.yologger.presentation.screen.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import com.yologger.domain.usecase.post.get_posts.PostData
import com.yologger.presentation.R

class PostsRVAdapter constructor(
    private val context: Context,
    private var posts: MutableList<PostData> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageAvatar: ImageView = itemView.findViewById(R.id.fragment_home_imageView_avatar)
        private val imageSlider: ImageSlider = itemView.findViewById(R.id.fragment_home_imageSlider)
        private val textViewNickname: TextView = itemView.findViewById(R.id.fragment_home_textView_nickname)
        private val textViewEmail: TextView = itemView.findViewById(R.id.fragment_home_textView_email)
        private val textViewContent: TextView = itemView.findViewById(R.id.fragment_home_holder_textView_content)

        fun bind(post: PostData) {
            textViewEmail.text = post.writerEmail
            textViewNickname.text = post.writerNickname
            textViewContent.text = post.content
            if (post.imageUrls == null) {
                imageSlider.visibility = View.GONE
            } else {
                imageSlider.visibility = View.VISIBLE
                imageSlider.adapter = SliderAdapter(context = context, GlideImageLoaderFactory(), imageUrls = post.imageUrls!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_holder_post, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Holder).bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    fun updatePosts(newPosts: List<PostData>) {
        posts = newPosts.toMutableList()
        notifyDataSetChanged()
    }
}