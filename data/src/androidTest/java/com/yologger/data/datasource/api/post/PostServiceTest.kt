package com.yologger.data.datasource.api.post

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.yologger.data.datasource.api.auth.AuthService
import com.yologger.data.datasource.api.auth.model.login.LoginRequest
import com.yologger.data.util.FileUtil
import com.yologger.data.util.TestUtil
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class PostServiceTest {

    @get:Rule
    val rule = HiltAndroidRule(this)

    @Inject lateinit var testUtil: TestUtil

    @Before
    fun setUp() {
        rule.inject()
    }

    @Test
    fun test_getPosts() {
        assertThat(testUtil.getSomething()).isEqualTo("something")
    }
}