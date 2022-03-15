package com.yologger.heart_to_heart.di

import android.content.Context
import com.google.gson.Gson
import com.yologger.data.datasource.pref.SessionStore
import com.yologger.data.util.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun providesSessionStore(@ApplicationContext appContext: Context): SessionStore {
        return SessionStore(appContext, "keyPref", "keyPrefSession")
    }

    @Singleton
    @Provides
    fun providesFileUtil(@ApplicationContext appContext: Context): FileUtil {
        return FileUtil(appContext)
    }
}