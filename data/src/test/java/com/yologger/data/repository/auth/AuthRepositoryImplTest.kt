package com.yologger.data.repository.auth

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.yologger.data.datasource.api.auth.AuthService
import com.yologger.data.datasource.api.auth.model.emailVerificationCode.EmailVerificationCodeSuccessResponse
import com.yologger.data.datasource.api.auth.model.join.JoinSuccessResponse
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.MockitoHelper.anyObject
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.usecase.auth.emailVerificationCode.EmailVerificationCodeResult
import com.yologger.domain.usecase.auth.join.JoinResult
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.mock.Calls

@RunWith(MockitoJUnitRunner::class)
class AuthRepositoryImplTest {

    @Mock
    lateinit var mockAuthService: AuthService

    @Mock
    lateinit var sessionStore: SessionStore

    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        mockAuthService = mock(AuthService::class.java)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `test emailVerification success`() {
        // Given
        `when`(mockAuthService.emailVerificationCode(anyObject()))
            .thenReturn(Calls.response(EmailVerificationCodeSuccessResponse("sented")))

        authRepository = AuthRepositoryImpl(mockAuthService, Gson(), sessionStore)

        // When
        val result = authRepository.emailVerificationCode("paul")

        // Then
        assertThat(result).isEqualTo(EmailVerificationCodeResult.SUCCESS)
    }

    @Test
    fun `test join success`() {
        // Given
        `when`(mockAuthService.join(anyObject()))
            .thenReturn(Calls.response(JoinSuccessResponse("1")))

        authRepository = AuthRepositoryImpl(mockAuthService, Gson(), sessionStore)

        // When
        val result = authRepository.join("ronaldo@gmail.com", "ronaldo", "cr7", "123asd1")

        // Then
        assertThat(result).isEqualTo(JoinResult.SUCCESS)
    }
}