package com.deathhit.data.breed

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule

@HiltAndroidTest
internal class BreedRepositoryTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun before() {
        hiltRule.inject()
    }
}