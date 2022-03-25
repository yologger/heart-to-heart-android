package com.yologger.common

object Constant {

    object Preference {
        const val KEY_PREFERENCE = "key_preference"
        const val KEY_THEME = "key_theme"
    }

    object Network {

        // const val BASE_URL = "http://172.30.1.52:8080"
        // const val BASE_URL = "http://10.0.2.2:8080"
        const val BASE_URL = BuildConfig.BASE_URL

        const val CONNECTION_TIMEOUT_DURATION = 5L
        const val READ_TIMEOUT_DURATION = 5L
        const val WRITE_TIMEOUT_DURATION = 5L
    }
}