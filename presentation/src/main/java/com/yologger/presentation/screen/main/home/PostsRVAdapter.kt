package com.yologger.presentation.screen.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ouattararomuald.slider.ImageSlider
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import com.yologger.domain.usecase.post.get_posts.PostData
import com.yologger.presentation.R

class PostsRVAdapter constructor(
    private val context: Context,
    private var posts: MutableList<PostData?> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageAvatar: ImageView = itemView.findViewById(R.id.fragment_home_imageView_avatar)
        private val imageSlider: ImageSlider = itemView.findViewById(R.id.fragment_home_imageSlider)
        private val textViewNickname: TextView = itemView.findViewById(R.id.fragment_home_textView_nickname)
        private val textViewEmail: TextView = itemView.findViewById(R.id.fragment_home_textView_email)
        private val textViewContent: TextView = itemView.findViewById(R.id.fragment_home_holder_textView_content)

        fun bind(post: PostData) {
            post.avatarUrl?.let { url ->
                Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(imageAvatar)
            }
            textViewEmail.text = post.writerEmail
            textViewNickname.text = post.writerNickname
            textViewContent.text = post.content
            if (post.imageUrls == null || post.imageUrls!!.size == 0) {
                imageSlider.visibility = View.GONE
            } else {
                imageSlider.visibility = View.VISIBLE
                imageSlider.adapter = SliderAdapter(context = context, GlideImageLoaderFactory(), imageUrls = post.imageUrls!!)
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {}
    }

    override fun getItemViewType(position: Int): Int {
        return if (posts[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_holder_post, parent, false)
                Holder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home_holder_post_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw RuntimeException("VIEW TYPE ERROR")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM) {
            (holder as Holder).bind(posts[position]!!)
        } else {
            (holder as LoadingViewHolder).bind()
        }
    }

    override fun getItemCount(): Int = posts.size

    fun updatePosts(newPosts: List<PostData>) {
        posts = newPosts.toMutableList()
        notifyDataSetChanged()
    }

    fun showLoadingView() {
        posts.add(null)
        notifyItemInserted(posts.size - 1)
    }

    fun hideLoadingView()  {
        if (posts.size != 0) {
            posts.removeAt(posts.size - 1)
            notifyItemRemoved(posts.size)
        }
    }
}