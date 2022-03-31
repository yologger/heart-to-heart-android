package com.yologger.domain.repository

import android.net.Uri
import com.yologger.domain.usecase.member.fetch_member_info.FetchMemberInfoResult
import com.yologger.domain.usecase.member.update_avatar.UpdateAvatarResult

interface MemberRepository {
    fun updateAvatar(imageUri: Uri): UpdateAvatarResult
    fun fetchMemberInfo(): FetchMemberInfoResult
}