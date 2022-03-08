package com.yologger.data.datasource.api.auth

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
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
    fun `test_emailVerificationCode`() {
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
        val request = EmailVerificationCodeRequest("ronaldo@gmail.com")
        val response = authService.emailVerificationCode(request).execute()

        // Then
        assertThat(response.isSuccessful).isTrue()
        assertThat(response.body()!!.message).isEqualTo("verified")
    }

    @Test
    fun `test_error`() {
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
            val errorResponse = gson.fromJson(it.string(), EmailVerificationCodeFailureResponse::class.java)
            assertThat(errorResponse.message).isEqualTo("Member Already Exists.")
            assertThat(errorResponse.code).isEqualTo(EmailVerificationCodeFailureCode.MEMBER_ALREADY_EXISTS)
        } ?: run {
            fail("errorBody() is null")
        }
}

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}