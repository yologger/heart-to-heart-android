package com.yologger.data.di

import com.google.gson.Gson
import com.yologger.common.Constant
import com.yologger.data.datasource.api.AuthInterceptor
import com.yologger.data.datasource.api.auth.AuthService
import com.yologger.data.datasource.api.member.MemberService
import com.yologger.data.datasource.api.post.PostService
import com.yologger.data.datasource.pref.SessionStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesAuthInterceptor(
        sessionStore: SessionStore,
        gson: Gson
    ): AuthInterceptor = AuthInterceptor(sessionStore, gson)

    @Singleton
    @OkHttpClientWithAuthInterceptor
    @Provides
    fun providesOkHttpClientWithAuthFilter(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constant.Network.CONNECTION_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .readTimeout(Constant.Network.READ_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .writeTimeout(Constant.Network.WRITE_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @OkHttpClientWithoutAnyInterceptor
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constant.Network.CONNECTION_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .readTimeout(Constant.Network.READ_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .writeTimeout(Constant.Network.WRITE_TIMEOUT_DURATION, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthService(@OkHttpClientWithoutAnyInterceptor okHttpClient: OkHttpClient): AuthService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constant.Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun providesPostService(@OkHttpClientWithAuthInterceptor okHttpClient: OkHttpClient): PostService {
        return Retrofit.Builder()
            .client(okHttpClient)
            // .baseUrl(Constant.BASE_URL)
            .baseUrl(Constant.Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)
    }

    @Singleton
    @Provides
    fun providesMemberService(@OkHttpClientWithAuthInterceptor okHttpClient: OkHttpClient): MemberService {
        return Retrofit.Builder()
            .client(okHttpClient)
            // .baseUrl(Constant.BASE_URL)
            .baseUrl(Constant.Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MemberService::class.java)
    }
}