package com.yologger.presentation.screen.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import com.yologger.domain.usecase.post.get_posts.PostData
import com.yologger.presentation.R
import com.yologger.presentation.databinding.FragmentHomeHolderPostBinding

class PostsRVAdapter constructor(
    private val context: Context,
    private var posts: MutableList<PostData?> = mutableListOf(),
    private val onItemClicked: (memberId: Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class Holder(val binding: FragmentHomeHolderPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostData) {
            post.avatarUrl?.let { url ->
                Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(binding.imageViewAvatar)
            }
            binding.textViewEmail.text = post.writerEmail
            binding.textViewNickname.text = post.writerNickname
            binding.textViewContent.text = post.content
            if (post.imageUrls == null || post.imageUrls!!.size == 0) {
                binding.imageSlider.visibility = View.GONE
            } else {
                binding.imageSlider.visibility = View.VISIBLE
                binding.imageSlider.adapter = SliderAdapter(context = context, GlideImageLoaderFactory(), imageUrls = post.imageUrls!!)
            }
            binding.viewUserInfo.setOnClickListener {
                posts[adapterPosition]?.let { postData ->
                    onItemClicked(postData.id)
                }
            }
//            binding.buttonMore.setOnClickListener {
//                val popupMenu = PopupMenu(context, binding.buttonMore)
//                popupMenu.menuInflater.inflate(R.menu.fragment_home_popup_menu, popupMenu.menu)
//                popupMenu.show()
//                Logger.w("clicked at ${adapterPosition}")
//            }
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
                val binding: FragmentHomeHolderPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.fragment_home_holder_post, parent, false)
                Holder(binding)
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