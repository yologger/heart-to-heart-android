package com.yologger.data.datasource.api.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthApiTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
    }

    @Test
    fun test() {
        // Given
        val baseUrl = server.url("/").toString()

        server.enqueue(MockResponse().setBody("Hello World!").setResponseCode(200))


        // When


        // Then
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}