package com.yologger.presentation.screen.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import com.yologger.domain.usecase.post.getPosts.PostData
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ItemFragmentHomePostBinding

class PostsRVAdapter constructor(
    private val context: Context,
    private var posts: MutableList<PostData?> = mutableListOf(),
    private val onUserInfoClicked: (memberId: Long) -> Unit,
    private val onReported: (memberId: Long) -> Unit,
    private val onBlocked: (memberId: Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class Holder(val binding: ItemFragmentHomePostBinding) : RecyclerView.ViewHolder(binding.root) {
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
                    onUserInfoClicked(postData.writerId)
                }
            }
            binding.buttonMore.setOnClickListener { view ->
                val popup = PopupMenu(context, view)
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.menu_fragment_home_popup, popup.menu)
                popup.setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.menu_fragment_home_popup_action_report -> {
                            posts[adapterPosition]?.let { postData ->
                                onReported(postData.writerId)
                            }
                            return@setOnMenuItemClickListener true
                        }
                        R.id.menu_fragment_home_popup_action_block -> {
                            posts[adapterPosition]?.let { postData ->
                                onBlocked(postData.writerId)
                            }
                            return@setOnMenuItemClickListener true
                        }
                        else -> return@setOnMenuItemClickListener false
                    }
                }
                popup.show()
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
                val binding: ItemFragmentHomePostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_fragment_home_post, parent, false)
                Holder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fragment_home_loading, parent, false)
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