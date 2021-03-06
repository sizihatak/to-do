package com.example.todo.test

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.v7.widget.RecyclerView
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.Matchers.allOf


open class ScreenRobot {
    protected fun <T : ScreenRobot> checkIsHiddend(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                    .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        }
        return this as T
    }

    protected fun <T : ScreenRobot> checkIsVisible(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
        return this as T
    }

    protected fun <T : ScreenRobot, VH: RecyclerView.ViewHolder>
            swipeItemOfRecyclerView(@IdRes viewId: Int, position: Int, action: ViewAction): T {
        onView(withId(viewId)).perform(
                RecyclerViewActions.actionOnItemAtPosition<VH>(position, action))
        return this as T
    }

    protected fun <T : ScreenRobot> checkViewHasText(@IdRes viewId: Int, @StringRes stringId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withText(stringId)))
        return this as T
    }

    protected fun <T : ScreenRobot> checkViewHasText(@IdRes viewId: Int, string: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(ViewMatchers.withText(string)))
        return this as T
    }

    protected fun <T : ScreenRobot> checkIsSelected(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                    .check(ViewAssertions.matches(ViewMatchers.isSelected()))
        }
        return this as T
    }

    protected fun <T : ScreenRobot> checkIsChecked(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                    .check(ViewAssertions.matches(ViewMatchers.isChecked()))
        }
        return this as T
    }

    protected fun <T : ScreenRobot> checkHint(@IdRes viewId: Int, string: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(withCustomHint(string)))
        return this as T
    }

    protected fun <T : ScreenRobot> checkError(@IdRes viewId: Int, string: String): T {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(withError(string)))
        return this as T
    }

    protected fun <T : ScreenRobot> checkBackgroundColor(@IdRes viewId: Int, color: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
                .check(ViewAssertions.matches(withBackgroundColor(color)))
        return this as T
    }

    protected fun <T : ScreenRobot> checkIsNotSelected(@IdRes vararg viewIds: Int): T {
        for (viewId in viewIds) {
            Espresso.onView(ViewMatchers.withId(viewId))
                    .check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isSelected())))
        }
        return this as T
    }

    protected fun <T : ScreenRobot> clickOn(@IdRes viewId: Int): T {
        Espresso.onView(ViewMatchers.withId(viewId))
                .perform(ViewActions.click())
        return this as T
    }

    protected fun <T : ScreenRobot> enterText(@IdRes viewId: Int, text: String): T {
        onView(ViewMatchers.withId(viewId)).perform(typeText(text))
        return this as T
    }

    protected fun <T : ScreenRobot> checkChildrenCount(@IdRes viewId: Int, count: Int): T {
        onView(withId(viewId)).check(matches(allOf(
                isDisplayed(),
                hasChildren(`is`(count))
        )))
        return this as T
    }


}