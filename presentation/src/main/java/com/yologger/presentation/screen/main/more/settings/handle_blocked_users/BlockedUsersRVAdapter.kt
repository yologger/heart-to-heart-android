package com.yologger.presentation.screen.main.more.settings.handle_blocked_users

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ActivityHandleBlockedUsersItemBinding
import com.yologger.presentation.screen.MemberData

class BlockedUsersRVAdapter constructor(
    private val context: Context
) : RecyclerView.Adapter<BlockedUsersRVAdapter.BlockedUserItem>() {
    val members = listOf(
        MemberData("Rachel", "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg"),
        MemberData("Monica", "https://i.pinimg.com/originals/dd/bd/43/ddbd43396b44aa648dc7619f0111a163.jpg"),
        MemberData("obama", "https://www.biography.com/.image/t_share/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg"),
        MemberData("kroos", "https://upload.wikimedia.org/wikipedia/commons/3/36/FIFA_WC-qualification_2014_-_Austria_vs._Germany_2012-09-11_-_Toni_Kroos.JPG"),
        MemberData("Rachel", "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg"),
        MemberData("Monica", "https://i.pinimg.com/originals/dd/bd/43/ddbd43396b44aa648dc7619f0111a163.jpg"),
        MemberData("obama", "https://www.biography.com/.image/t_share/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg"),
        MemberData("kroos", "https://upload.wikimedia.org/wikipedia/commons/3/36/FIFA_WC-qualification_2014_-_Austria_vs._Germany_2012-09-11_-_Toni_Kroos.JPG"),
        MemberData("Rachel", "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg"),
        MemberData("Monica", "https://i.pinimg.com/originals/dd/bd/43/ddbd43396b44aa648dc7619f0111a163.jpg"),
        MemberData("obama", "https://www.biography.com/.image/t_share/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg"),
        MemberData("kroos", "https://upload.wikimedia.org/wikipedia/commons/3/36/FIFA_WC-qualification_2014_-_Austria_vs._Germany_2012-09-11_-_Toni_Kroos.JPG"),
        MemberData("Rachel", "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg"),
        MemberData("Monica", "https://i.pinimg.com/originals/dd/bd/43/ddbd43396b44aa648dc7619f0111a163.jpg"),
        MemberData("obama", "https://www.biography.com/.image/t_share/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg"),
        MemberData("kroos", "https://upload.wikimedia.org/wikipedia/commons/3/36/FIFA_WC-qualification_2014_-_Austria_vs._Germany_2012-09-11_-_Toni_Kroos.JPG"),
        MemberData("Rachel", "https://s-media-cache-ak0.pinimg.com/736x/c1/e5/c1/c1e5c1ee11ebcaf5ce093b1c08304614.jpg"),
        MemberData("Monica", "https://i.pinimg.com/originals/dd/bd/43/ddbd43396b44aa648dc7619f0111a163.jpg"),
        MemberData("obama", "https://www.biography.com/.image/t_share/MTE4MDAzNDEwNzg5ODI4MTEw/barack-obama-12782369-1-402.jpg"),
        MemberData("kroos", "https://upload.wikimedia.org/wikipedia/commons/3/36/FIFA_WC-qualification_2014_-_Austria_vs._Germany_2012-09-11_-_Toni_Kroos.JPG")
    )

    inner class BlockedUserItem(val binding: ActivityHandleBlockedUsersItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.textViewNickname.text = members[position].nickname
            Glide.with(context).load(members[position].avatarUrl).into(binding.imageViewAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedUserItem {
        val binding: ActivityHandleBlockedUsersItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.activity_handle_blocked_users_item, parent, false)
        return BlockedUserItem(binding)
    }

    override fun onBindViewHolder(holder: BlockedUserItem, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = members.size
}