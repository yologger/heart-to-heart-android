object Dependencies {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val LOGGER = "com.orhanobut:logger:${Versions.LOGGER}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val RXJAVA = "io.reactivex.rxjava3:rxjava:${Versions.RXJAVA}"
    const val RXKOTLIN = "io.reactivex.rxjava3:rxkotlin:${Versions.RXKOTLIN}"
    const val RXANDROID = "io.reactivex.rxjava3:rxandroid:${Versions.RXANDROID}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${Versions.ACTIVITY_KIX}"
    const val FRAGMENT_KTX =  "androidx.fragment:fragment-ktx:${Versions.FRAGMENT_KTX}"
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment:${Versions.NAVIGATION}"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui:${Versions.NAVIGATION}"
    const val RETROFIT2 = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT2}"
    const val RETROFIT_RXJAVA3_ADAPTER = "com.squareup.retrofit2:adapter-rxjava3:${Versions.RETROFIT_RXJAVA3_ADAPTER}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT_GSON_CONVERTER}"
    const val TED_IMAGE_PICKER = "io.github.ParkSangGwon:tedimagepicker:${Versions.TED_IMAGE_PICKER}"
    const val GLIDE = "com.github.bumptech.glide:glide:${Versions.GLIDE}"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:${Versions.GLIDE}"
}

object TestDependencies {
    const val JUNIT = "junit:junit:${TestVersions.JUNIT}"
    const val TRUTH = "com.google.truth:truth:${TestVersions.TRUTH}"
    const val ANDROIDX_TEST_CORE = "androidx.arch.core:core-testing:${TestVersions.ANDROIDX_TEST_CORE}"
    const val MOCK_WEB_SERVER = "com.squareup.okhttp3:mockwebserver:${TestVersions.MOCK_WEB_SERVER}"
    const val MOCKITO_CORE = "org.mockito:mockito-core:${TestVersions.MOCKITO_CORE}"
    const val MOCKITO_INLINE = "org.mockito:mockito-inline:${TestVersions.MOCKITO_INLINE}"
}

object AndroidTestDependencies {
    const val JUNIT = "androidx.test.ext:junit:${AndroidTestVersions.JUNIT}"
    const val ANDROIDX_TEST_EXTENSION_TRUTH = "androidx.test.ext:truth:${AndroidTestVersions.ANDROIDX_TEST_EXTENSION_TRUTH}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${AndroidTestVersions.ESPRESSO_CORE}"
    const val MOCKITO_ANDROID = "org.mockito:mockito-android:${AndroidTestVersions.MOCKITO_ANDROID}"
}