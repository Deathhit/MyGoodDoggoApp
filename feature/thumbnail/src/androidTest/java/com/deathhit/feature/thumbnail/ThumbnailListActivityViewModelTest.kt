package com.deathhit.feature.thumbnail

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
class ThumbnailListActivityViewModelTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun before() {
        hiltRule.inject()


    }
}