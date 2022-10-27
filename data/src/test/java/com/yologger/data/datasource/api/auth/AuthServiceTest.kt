package com.yologger.data.datasource.api.auth

import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

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
    fun `테스트`() {
        // Given
    }

//    @Test
//    @Ignore
//    fun `테스트`() {
//        // Given
//        val mockResponse = MockResponse()
//            .setResponseCode(HttpURLConnection.HTTP_OK)
//            .setBody("{\"message\":\"sent\"}")
//
//        mockServer.enqueue(mockResponse)
//
//        val okHttpClient = OkHttpClient.Builder()
//            .build()
//
//        val retrofit = Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl(mockUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        authService = retrofit.create(AuthService::class.java)
//
//        // When
//        val request = EmailVerificationCodeRequest("ronaldo@gmail.com")
//        val response = authService.emailVerificationCode(request).execute()
//
//        // Then
//        assertThat(response.isSuccessful).isTrue()
//        assertThat(response.body()!!.message).isEqualTo("verified")
//    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}