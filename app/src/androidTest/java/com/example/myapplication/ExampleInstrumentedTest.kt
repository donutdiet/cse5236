package com.example.myapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myapplication.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity ::class.java)

    @Test
    fun activity_login__elementsAreDisplayed() {
        onView(withId(R.id.titleText))
            .check(matches(isDisplayed()))
            .check(matches(withText("Login")))

        onView(withId(R.id.emailEditText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.passwordEditText))
            .check(matches(isDisplayed()))

        onView(withId(R.id.loginButton))
            .check(matches(isDisplayed()))
            .check(matches(withText("Log In")))

        onView(withId(R.id.registerLink))
            .check(matches(isDisplayed()))
            .check(matches(withText("Don’t have an account? Register")))
    }

    @Test
    fun userCanEnterEmailAndPassword() {
        onView(withId(R.id.emailEditText))
            .perform(clearText(), typeText("test@example.com"), closeSoftKeyboard())

        onView(withId(R.id.passwordEditText))
            .perform(clearText(), typeText("mypassword123"), closeSoftKeyboard())

        onView(withId(R.id.emailEditText))
            .check(matches(withText("test@example.com")))

        onView(withId(R.id.passwordEditText))
            .check(matches(withText("mypassword123")))
    }

    @Test
    fun loginButton_isClickable() {
        onView(withId(R.id.loginButton))
            .perform(click())
    }

    @Test
    fun registerLink_isClickable() {
        onView(withId(R.id.registerLink))
            .perform(click())
    }

}