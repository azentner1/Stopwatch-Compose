package com.hubstaff.challenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.hubstaff.challenge.screen.MainActivity
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
class LoginComponentTest {

    @get:Rule
    val composeRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun loginTest() {
        val button = composeRule.onNode(hasTestTag("btnLogin"),true)
        button.assertIsDisplayed()
        button.performClick()
        button.performClick()
    }
}