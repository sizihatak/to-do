package com.example.todo.test

import android.graphics.drawable.ColorDrawable
import android.support.design.widget.TextInputLayout
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import java.lang.reflect.InvocationTargetException




fun withCustomHint(s: String): Matcher<View> {
    return object : BaseMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("text: $s")
        }

        override fun matches(item: Any): Boolean {
            try {
                val method = item.javaClass.getMethod("getHint")
                return s == method.invoke(item)
            } catch (e: NoSuchMethodException) {
            } catch (e: InvocationTargetException) {
            } catch (e: IllegalAccessException) {
            }
            return false
        }
    }
}

fun withBackgroundColor(color: Int): Matcher<View> {
    return object : BaseMatcher<View>() {
        override fun matches(item: Any): Boolean {
            try {
                val method = item.javaClass.getMethod("getBackground")
                return color == ((method.invoke(item) as ColorDrawable)).color
            } catch (e: NoSuchMethodException) {
            } catch (e: InvocationTargetException) {
            } catch (e: IllegalAccessException) {
            }
            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("color: $color")
        }

    }
}

fun withError(s: String): Matcher<View> {
    return object : BoundedMatcher<View, TextInputLayout>(TextInputLayout::class.java) {
        internal var actualError = ""

        override fun describeTo(description: Description) {
            description.appendText("text: $s")
        }

        public override fun matchesSafely(textInputLayout: TextInputLayout): Boolean {
            val error = textInputLayout.error
            if (error != null) {
                return s == error.toString()
            }
            return false
        }
    }
}