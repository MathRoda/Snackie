package com.mathroda.snackie

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mathroda.snackie.TestTags.SNACKIE
import com.mathroda.snackie.TestTags.SNACKIE_MESSAGE
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SnackieTests {

    @get:Rule
    val composeRule = createComposeRule()
    
    private lateinit var state: SnackieState
    
    @Test
    fun assert_Snackie_IsNotVisible() {
        composeRule.setContent {
            state = rememberSnackieState()
            SnackieCustom(state = state)
        }

        composeRule.onNodeWithTag(SNACKIE).assertDoesNotExist()
    }

    @Test
    fun assert_Snackie_isVisible() {
        composeRule.setContent {
            state = rememberSnackieState()
            state.addMessage("Snackie is Visible")
            SnackieCustom(state = state)
        }

        composeRule.onNodeWithTag(SNACKIE_MESSAGE).assertTextEquals("Snackie is Visible")
    }
}