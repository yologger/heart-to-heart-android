package com.yologger.data.datasource.api.test

import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TestApiTest {

    lateinit var server: MockWebServer
    lateinit var mockUrl: String
    lateinit var testApi: TestApi

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        mockUrl = server.url("").toString()
        testApi = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .baseUrl(mockUrl)
            .build()
            .create(TestApi::class.java)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun test1() {
        // Given
        val mockResponse = MockResponse().apply {
            setResponseCode(200)
            setBody("{\"data\":\"test\"}")
        }
        server.enqueue(mockResponse)

        // When
        val response = testApi.test1().execute()

        // Then
        if (response.isSuccessful) {
            assertThat(response.body()!!.data).isEqualTo("test")
        } else {
            fail(response.errorBody().toString())
        }
    }

    @Test
    fun test2() {
        // Given
        val dispatcher = object: Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when(request.path) {
                    "/test/test1" -> MockResponse().apply {
                        setResponseCode(200)
                        setBody("{\"data\":\"test1\"}")
                    }
                    "/test/test2" -> MockResponse().apply {
                        setResponseCode(200)
                        setBody("{\"data\":\"test2\"}")
                    }
                    else -> MockResponse().apply {
                        setResponseCode(404)
                        setBody("{\"error\":\"Not Found\"}")
                    }
                }
            }
        }
        server.dispatcher = dispatcher

        // When
        val response1 = testApi.test1().execute()
        val response2 = testApi.test2().execute()

        // Then
        assertThat(response1!!.body()!!.data).isEqualTo("test1")
        assertThat(response2!!.body()!!.data).isEqualTo("test2")
    }
}