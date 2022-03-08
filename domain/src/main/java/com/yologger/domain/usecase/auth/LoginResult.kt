package com.yologger.domain.usecase.auth

data class LoginResult(
    val accessToken: String,
    val refreshToken: String,
)