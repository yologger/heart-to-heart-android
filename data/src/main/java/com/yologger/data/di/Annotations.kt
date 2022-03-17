package com.yologger.data.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientWithoutAnyInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpClientWithAuthInterceptor