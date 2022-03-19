package com.yologger.data.datasource.api.auth

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.yologger.data.datasource.api.auth.model.confirm_verification_code.ConfirmVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeFailureResponseCode
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeFailureResponse
import com.yologger.data.datasource.api.auth.model.email_verification_code.EmailVerificationCodeRequest
import com.yologger.data.datasource.api.auth.model.join.JoinRequest
import com.yologger.data.datasource.api.auth.model.login.LoginRequest
import com.yologger.data.datasource.api.auth.model.reissue_token.ReissueTokenRequest
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection


class AuthServiceTest {

    private lateinit var authService: AuthService
    private lateinit var mockServer: MockWebServer
    private lateinit var mockUrl: HttpUrl

    @Before
    fun setUp() {
        mockServer = MockWebServer()
        mockServer.start()
        mockUrl = mockServer.url("/")
    }

    @Test
    @Ignore
    fun `test emailVerificationCode success response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"message\":\"sent\"}")

        mockServer.enqueue(mockResponse)

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(mockUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)

        // When
        val request = EmailVerificationCodeRequest("ronaldo@gmail.com")
        val response = authService.emailVerificationCode(request).execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()!!.message).isEqualTo("verified")
    }

    @Test
    @Ignore
    fun `test emailVerificationCode failure response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody("{\"status\":400,\"message\":\"Member Already Exists.\",\"code\":\"A001\"}")

        mockServer.enqueue(mockResponse)

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(mockUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)

        // When
        val request = EmailVerificationCodeRequest("ronaldo@gmail.com")
        val response = authService.emailVerificationCode(request).execute()

        // Then
        assertThat(response.isSuccessful).isFalse()
        response.errorBody()?.let {
            val gson = Gson()
            val errorResponse =
                gson.fromJson(it.string(), EmailVerificationCodeFailureResponse::class.java)
            assertThat(errorResponse.message).isEqualTo("Member Already Exists.")
            assertThat(errorResponse.code).isEqualTo(EmailVerificationCodeFailureResponseCode.MEMBER_ALREADY_EXIST)
        } ?: run {
            fail("errorBody() is null")
        }
    }

    @Test
    @Ignore
    fun `test confirmVerificationCode success response`() {
        // Given
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("{\"message\":\"verified\"}")

        mockServer.enqueue(mockResponse)

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(mockUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)

        // When
        val request = ConfirmVerificationCodeRequest("ronaldo@gmail.com", "weqwqe213")
        val response = authService.confirmVerificationCode(request).execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()!!.message).isEqualTo("verified")
    }

    @Test
    @Ignore
    fun `test join fail`() {
        // Given
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)

        // When
        val request = JoinRequest("ronaldo@gmail.com", "", "", "")
        val response = authService.join(request).execute()

        // Then
        assertThat(response.isSuccessful).isFalse()
    }

    @Test
    @Ignore
    fun `test login failure`() {
        // Given
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)

        // When
        val request = LoginRequest(email= "yologger1013.dev@gmail.com", "123213")
        val response = authService.login(request).execute()

        // Then
        assertThat(response.isSuccessful).isFalse()
    }

    @Test
    @Ignore
    fun `test`() {
        // Given
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)

        // When
        val response = authService.logout("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZW1iZXIiLCJuYW1lIjoieW9sb2dnZXJkZXYiLCJuaWNrbmFtZSI6ImRldiIsImlkIjozLCJleHAiOjE2NDY5Mzg0MjgsImVtYWlsIjoieW9sb2dnZXIxMDEzLmRldkBnbWFpbC5jb20ifQ.xT4t1RsJyJ9etgxscB0M1zxW2e96kHUFwe4wGuqj0yM").execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    @Ignore
    fun `test verifyAccessToken`() {
        // Given
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)

        // When
        val response = authService.verifyAccessToken("Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZW1iZXIiLCJuYW1lIjoieW9sb2dnZXJkZXYiLCJuaWNrbmFtZSI6ImRldiIsImlkIjozLCJleHAiOjE2NDY5Mzg0MjgsImVtYWlsIjoieW9sb2dnZXIxMDEzLmRldkBnbWFpbC5jb20ifQ.xT4t1RsJyJ9etgxscB0M1zxW2e96kHUFwe4wGuqj0yM").execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
    }

    @Test
    fun `reissueToken`() {
        // Given
        val okHttpClient = OkHttpClient.Builder()
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        authService = retrofit.create(AuthService::class.java)

        // When
        val request = ReissueTokenRequest(memberId = 3, refreshToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtZW1iZXIiLCJuaWNrbmFtZSI6ImRldiIsImZ1bGxOYW1lIjoieW9sb2dnZXJkZXYiLCJpZCI6MywiZXhwIjoxNjQ3NTkxNjM3LCJlbWFpbCI6InlvbG9nZ2VyMTAxMy5kZXZAZ21haWwuY29tIn0.udX1btuzRoRFncP-ZL3CVzmSz6kxE9UEQX_wVWme0c8")
        val response = authService.reissueToken(request).execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}