package com.yologger.data.repository.auth

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.yologger.data.datasource.api.auth.AuthService
import com.yologger.data.datasource.api.auth.EmailVerificationCodeResponse
import com.yologger.data.util.MockitoHelper.anyObject
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.usecase.email_verification_code.EmailVerificationCodeResult
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
            .thenReturn(Calls.response(EmailVerificationCodeResponse("sented")))

        authRepository = AuthRepositoryImpl(mockAuthService, Gson())

        // When
        val result = authRepository.emailVerificationCode("paul")

        // Then
        assertThat(result).isEqualTo(EmailVerificationCodeResult.SUCCESS)
    }
}