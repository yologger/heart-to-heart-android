package com.yologger.domain.repository

import android.net.Uri
import com.yologger.domain.usecase.member.blockMember.BlockMemberResult
import com.yologger.domain.usecase.member.deleteAccount.DeleteAccountResult
import com.yologger.domain.usecase.member.fetchMemberInfo.FetchMemberInfoResult
import com.yologger.domain.usecase.member.getBlockingMembers.GetBlockingMembersResult
import com.yologger.domain.usecase.member.getMeId.GetMeIdResult
import com.yologger.domain.usecase.member.reportMember.ReportMemberResult
import com.yologger.domain.usecase.member.unblockMember.UnblockMemberResult
import com.yologger.domain.usecase.member.updateAvatar.UpdateAvatarResult

interface MemberRepository {
    fun updateAvatar(imageUri: Uri): UpdateAvatarResult
    fun fetchMemberInfo(): FetchMemberInfoResult
    fun fetchMemberInfo(memberId: Long): FetchMemberInfoResult
    fun blockMember(targetId: Long): BlockMemberResult
    fun reportMember(targetId: Long): ReportMemberResult
    fun getBlockingMembers(): GetBlockingMembersResult
    fun unblockMember(targetId: Long): UnblockMemberResult
    fun deleteAccount(): DeleteAccountResult
    fun getMeId(): GetMeIdResult
}