package com.timurpehlivan.themoviedb.presentation.movielist

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.timurpehlivan.themoviedb.BaseUITest
import com.timurpehlivan.themoviedb.R
import org.hamcrest.CoreMatchers
import org.junit.Test

class MovieListFragmentTest : BaseUITest() {
  @Test
  fun displayScreenTitle() {
    assertDisplayed("The Movie DB")
  }

  @Test
  fun searchMovieAndNavigateDetailFragment() {
    Espresso.onView(withId(R.id.action_search)).perform(ViewActions.click())
    Espresso.onView(withId(R.id.search_src_text)).perform(ViewActions.typeText("TEST"))
    Thread.sleep(2000) // wait for the image loading
    Espresso.onView(
      CoreMatchers.allOf(
        withId(R.id.movie_poster),
        ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.search_movie_list_recycler_view), 0))
      )
    ).perform(ViewActions.click())
    assertDisplayed(R.id.detail_root)
  }
}