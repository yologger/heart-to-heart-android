package com.yologger.data.datasource.pref

data class Session constructor(
    var memberId: Long,
    var email: String,
    var name: String,
    var nickname: String,
    var accessToken: String,
    var refreshToken: String,
    var avatarUrl: String? = null
)