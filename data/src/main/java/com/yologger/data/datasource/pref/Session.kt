package com.yologger.data.datasource.pref

data class Session constructor(
    val memberId: Long,
    val email: String,
    val name: String,
    val nickname: String,
    val accessToken: String,
    val refreshToken: String,
    val avatarUrl: String? = null
)