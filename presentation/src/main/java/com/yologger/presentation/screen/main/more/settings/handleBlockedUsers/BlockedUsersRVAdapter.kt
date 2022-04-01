package com.yologger.presentation.screen.main.more.settings.handleBlockedUsers

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yologger.domain.usecase.member.getBlockingMembers.MemberData
import com.yologger.presentation.R
import com.yologger.presentation.databinding.ItemActivityHandleBlockedUsersUserBinding

class BlockedUsersRVAdapter constructor(
    private val context: Context,
    private var members: MutableList<MemberData> = mutableListOf(),
    private val onUnblocked: (memberId: Long) -> Unit
) : RecyclerView.Adapter<BlockedUsersRVAdapter.BlockedUserItem>() {

    inner class BlockedUserItem(val binding: ItemActivityHandleBlockedUsersUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.textViewNickname.text = members[position].nickname
            members[position].avatarUrl?.let { url ->
                Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .into(binding.imageViewAvatar)
            }
            binding.buttonUnblock.setOnClickListener {
                onUnblocked(members[position].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockedUserItem {
        val binding: ItemActivityHandleBlockedUsersUserBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_activity_handle_blocked_users_user, parent, false)
        return BlockedUserItem(binding)
    }

    override fun onBindViewHolder(holder: BlockedUserItem, position: Int) = holder.bind(position)

    override fun getItemCount(): Int = members.size

    fun updateMembers(members: List<MemberData>) {
        this.members = members.toMutableList()
        notifyDataSetChanged()
    }
}