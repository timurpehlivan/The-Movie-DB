package com.timurpehlivan.themoviedb

import android.view.View
import android.view.ViewGroup
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.timurpehlivan.themoviedb.presentation.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseUITest {

  val mActivityRule = ActivityTestRule(MainActivity::class.java)
    @Rule get

  fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("position $childPosition of parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        if (view.parent !is ViewGroup) return false
        val parent = view.parent as ViewGroup

        return (parentMatcher.matches(parent)
            && parent.childCount > childPosition
            && parent.getChildAt(childPosition) == view)
      }
    }
  }
}