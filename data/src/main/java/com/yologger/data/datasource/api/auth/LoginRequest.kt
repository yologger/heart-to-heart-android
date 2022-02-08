package com.yologger.data.datasource.api.auth

data class LoginRequest
constructor(
    val email: String,
    val password: String
)