package com.yologger.data.di

import com.google.gson.Gson
import com.yologger.common.Constant
import com.yologger.data.datasource.api.AuthInterceptor
import com.yologger.data.datasource.api.auth.AuthService
import com.yologger.data.datasource.api.post.PostService
import com.yologger.data.datasource.pref.SessionStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
            // .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @OkHttpClientWithoutAnyInterceptor
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthService(@OkHttpClientWithoutAnyInterceptor okHttpClient: OkHttpClient): AuthService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    @Singleton
    @Provides
    fun providesPostService(@OkHttpClientWithAuthInterceptor okHttpClient: OkHttpClient): PostService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostService::class.java)
    }
}