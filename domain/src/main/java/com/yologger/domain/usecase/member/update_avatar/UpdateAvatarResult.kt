package com.yologger.domain.usecase.member.update_avatar

sealed class UpdateAvatarResult {
    data class Success(val avatarUrl: String): UpdateAvatarResult()
    data class Failure(val error: UpdateAvatarResultError): UpdateAvatarResult()
}
