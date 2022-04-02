package com.yologger.presentation.screen.main.more.showMyPosts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ouattararomuald.slider.SliderAdapter
import com.ouattararomuald.slider.loaders.glide.GlideImageLoaderFactory
import com.yologger.domain.usecase.post.getAllPosts.PostData
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ItemActivityShowMyPostsPostBinding

class MyPostsRVAdapter constructor(
    private val context: Context,
    private var posts: MutableList<PostData?> = mutableListOf(
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        ),
//        PostData(
//            id = 1,
//            writerId = 1,
//            writerEmail = "paul@gmail.com",
//            writerNickname = "paul",
//            avatarUrl = "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg",
//            content = "content1",
//            imageUrls = arrayListOf("https://i.pinimg.com/originals/00/9e/59/009e59b9df936efc79f2089d55181766.jpg", "https://images.pexels.com/photos/688660/pexels-photo-688660.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"),
//            createdAt = "2022-04-03 02:38:28.502000",
//            updatedAt = "2022-04-03 02:38:28.502000"
//        )
    ),
    private val onDeleted: (postId: Long) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    inner class Holder(val binding: ItemActivityShowMyPostsPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostData) {
            post.avatarUrl?.let { url ->
                Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(binding.imageViewAvatar)
            } ?: let {
                Glide.with(context)
                    .load(R.drawable.image_avatar_default)
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
            binding.buttonDeletePost.setOnClickListener { view ->
                posts[adapterPosition]?.let { postData ->
                    onDeleted(postData.id)
                }
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
                val binding: ItemActivityShowMyPostsPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_activity_show_my_posts_post, parent, false)
                Holder(binding)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_show_my_posts_loading, parent, false)
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