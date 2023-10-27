package com.firstdueapplication.ui.activity

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import com.firstdueapplication.R
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test()
    fun checkFragment_expectedLogin() {

        launchActivity<MainActivity>()

        onView(withId(R.id.tvWelcome)).check(matches(allOf(isDisplayed(), withText("Welcome!"))))

    }

    @Test
    fun checkLogin_input_empty_email_expected_error() {

        waitFun()
        onView(withId(R.id.etEmail)).perform(clearText())
        onView(withId(R.id.etPassword)).perform(clearText())
        // onView(withId(R.id.tvError)).check(matches(isDisplayed())).perform(typeText(""))
        waitFun()
        onView(withId(R.id.etEmail)).perform(typeText(""))
        onView(withId(R.id.etPassword)).perform(typeText("jiten"))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())
        waitFun()
        onView(withId(R.id.tvError)).check(matches(withText("Please enter email")))
    }

    @Test
    fun checkLogin_input_empty_password_expected_error() {

        onView(withId(R.id.etEmail)).perform(clearText())
        onView(withId(R.id.etPassword)).perform(clearText())
        // onView(withId(R.id.tvError)).check(matches(isDisplayed())).perform(typeText(""))
        waitFun()
        onView(withId(R.id.etEmail)).perform(typeText("jiten@gmail.com"))
        onView(withId(R.id.etPassword)).perform(typeText(""))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())
        waitFun()
        onView(withId(R.id.tvError)).check(matches(withText("Please enter password")))
    }

    @Test
    fun checkLogin_input_invalid_email_expected_error() {

        onView(withId(R.id.etEmail)).perform(clearText())
        onView(withId(R.id.etPassword)).perform(clearText())
        // onView(withId(R.id.tvError)).check(matches(isDisplayed())).perform(typeText(""))
        waitFun()
        onView(withId(R.id.etEmail)).perform(typeText("jiten@gmail"))
        onView(withId(R.id.etPassword)).perform(typeText("jiten"))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())
        waitFun()
        onView(withId(R.id.tvError)).check(matches(withText("Please enter valid email")))
    }

    @Test
    fun checkLogin_input_password_lenthshort_expected_error() {

        onView(withId(R.id.etEmail)).perform(clearText())
        onView(withId(R.id.etPassword)).perform(clearText())
        // onView(withId(R.id.tvError)).check(matches(isDisplayed())).perform(typeText(""))
        waitFun()
        onView(withId(R.id.etEmail)).perform(typeText("jiten@gmail"))
        onView(withId(R.id.etPassword)).perform(typeText("ji"))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())
        waitFun()
        onView(withId(R.id.tvError)).check(matches(withText("Password should character length should be fall in 5 to 15")))
    }

    @Test
    fun checkLogin_input_password_lenthexceed_expected_error() {

        onView(withId(R.id.etEmail)).perform(clearText())
        onView(withId(R.id.etPassword)).perform(clearText())
        // onView(withId(R.id.tvError)).check(matches(isDisplayed())).perform(typeText(""))
        waitFun()
        onView(withId(R.id.etEmail)).perform(typeText("jiten@gmail"))
        onView(withId(R.id.etPassword)).perform(typeText("jitenramenchhaganbhairamoliya"))
        Espresso.closeSoftKeyboard()

        onView(withId(R.id.btnLogin)).perform(click())
        waitFun()
        onView(withId(R.id.tvError)).check(matches(withText("Password should character length should be fall in 5 to 15")))
    }

    @Test
    fun transitRegister() {
        onView(withId(R.id.btnSignUp)).perform(click())
        waitFun()
        onView(withId(R.id.tvCreateAcc)).check(
            matches(
                allOf(
                    isDisplayed(),
                    withText("Create New Account")
                )
            )
        )
    }

    fun waitFun(value: Long = 150) {
        Thread.sleep(value)
    }

}