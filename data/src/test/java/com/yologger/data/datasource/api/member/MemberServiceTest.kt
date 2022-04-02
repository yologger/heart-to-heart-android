package com.yologger.data.datasource.api.member

import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import org.junit.Ignore
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MemberServiceTest {

    private lateinit var memberService: MemberService

    @Test
    @Ignore
    fun `test getMemberInfo success response`() {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://172.30.1.3:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val memberService = retrofit.create(MemberService::class.java)

        val response = memberService.getMemberProfile(7).execute()

        assertThat(response.isSuccessful).isTrue()
    }
}