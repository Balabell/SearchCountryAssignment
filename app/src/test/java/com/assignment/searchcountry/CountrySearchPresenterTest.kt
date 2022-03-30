package com.assignment.searchcountry

import android.content.Context
import com.assignment.searchcountry.model.Coord
import com.assignment.searchcountry.model.Country
import com.assignment.searchcountry.presenter.CountrySearchInterface
import com.assignment.searchcountry.presenter.CountrySearchPresenter
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CountrySearchPresenterTest {

    @Mock
    private lateinit var context: Context

    @Mock
    lateinit var view: CountrySearchInterface

    @InjectMocks
    lateinit var presenter: CountrySearchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    private val countryList = listOf(
        Country("AU", "North Sydney", 0, Coord(-33.8, 151.2)),
        Country("US", "Seattle", 1, Coord(47.6, -122.3))
    )

    @Test
    fun testSearchNotFound() {
        // given
        val input = "cxs"

        // when
        presenter.onQueryTextChange(input, countryList)

        // then
        verify(view).showLoading()
        verify(view).onSearchNotFound()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun testSearchWhenInputIsEmpty() {
        // given
        val input = ""

        // when
        presenter.onQueryTextChange(input, countryList)

        // then
        verify(view).showLoading()
        verify(view).updateCountryList(countryList)
        verify(view).onSearchFound()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun testSearchWhenInputIsNull() {
        // given
        val input = null

        // when
        presenter.onQueryTextChange(input, countryList)

        // then
        verify(view).showLoading()
        verify(view).updateCountryList(countryList)
        verify(view).onSearchFound()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun testSearchFound() {
        // given
        val input = "nor"
        val expectedCountryFilterList = countryList
            .filter { it.name.contains(input, true) }
            .toMutableList()

        // when
        presenter.onQueryTextChange(input, countryList)

        // then
        verify(view).showLoading()
        verify(view).updateCountryList(expectedCountryFilterList)
        verify(view).onSearchFound()
        verifyNoMoreInteractions(view)
    }
}