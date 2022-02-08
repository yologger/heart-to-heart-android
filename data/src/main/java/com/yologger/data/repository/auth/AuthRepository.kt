package com.yologger.data.repository.auth

import com.yologger.data.datasource.api.auth.AuthApi
import com.yologger.domain.usecase.auth.LoginData

class AuthRepository constructor(
    val authApi: AuthApi
) {
    fun login(loginData: LoginData) {

    }
}