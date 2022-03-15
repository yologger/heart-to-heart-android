package com.yologger.heart_to_heart.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientWithoutAnyInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientWithAuthInterceptor