object Dependencies {
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val LOGGER = "com.orhanobut:logger:${Versions.LOGGER}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
}

object TestDependencies {
    const val JUNIT = "junit:junit:${TestVersions.JUNIT}"
}

object AndroidTestDependencies {
    const val JUNIT = "androidx.test.ext:junit:${AndroidTestVersions.JUNIT}"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${AndroidTestVersions.ESPRESSO_CORE}"
}