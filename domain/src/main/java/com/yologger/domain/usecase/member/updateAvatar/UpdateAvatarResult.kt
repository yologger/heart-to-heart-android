package com.yologger.domain.usecase.member.updateAvatar

sealed class UpdateAvatarResult {
    data class Success(val avatarUrl: String): UpdateAvatarResult()
    data class Failure(val error: UpdateAvatarResultError): UpdateAvatarResult()
}
