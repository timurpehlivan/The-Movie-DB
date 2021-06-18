package com.timurpehlivan.themoviedb.presentation.moviedetails

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions
import com.timurpehlivan.themoviedb.BaseUITest
import com.timurpehlivan.themoviedb.R
import org.hamcrest.CoreMatchers
import org.junit.Test

class MovieDetailsFragmentTest : BaseUITest() {
  @Test
  fun testRetailedMovieNavigation() {
    searchMovieAndNavigateDetailFragment()
    Thread.sleep(2000) // wait for the image loading
    onView(withId(R.id.scroll_view)).perform(swipeUp())
    onView(
      CoreMatchers.allOf(
        withId(R.id.movie_poster),
        isDescendantOfA(nthChildOf(withId(R.id.related_movie_list_recycler_view), 0))
      )
    ).perform(click())
    BaristaVisibilityAssertions.assertDisplayed(R.id.detail_root)
  }

  private fun searchMovieAndNavigateDetailFragment() {
    onView(withId(R.id.action_search)).perform(ViewActions.click())
    onView(withId(R.id.search_src_text)).perform(ViewActions.typeText("TEST"))
    Thread.sleep(2000) // wait for the image loading
    onView(
      CoreMatchers.allOf(
        withId(R.id.movie_poster),
        isDescendantOfA(nthChildOf(withId(R.id.search_movie_list_recycler_view), 0))
      )
    ).perform(ViewActions.click())
    BaristaVisibilityAssertions.assertDisplayed(R.id.detail_root)
  }
}