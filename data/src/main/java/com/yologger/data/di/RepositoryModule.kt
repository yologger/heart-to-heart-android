package com.yologger.data.di

import com.yologger.data.repository.auth.AuthRepositoryImpl
import com.yologger.data.repository.post.PostRepositoryImpl
import com.yologger.domain.repository.AuthRepository
import com.yologger.domain.repository.PostRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun bindsPostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository
}