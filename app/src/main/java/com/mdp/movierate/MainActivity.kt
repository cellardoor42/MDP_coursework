package com.mdp.movierate

import android.app.Activity
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.design.widget.BottomNavigationView.*
import android.support.v7.app.AppCompatActivity
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.XML
//import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType
import khttp.get as khttpGet

class MainActivity : AppCompatActivity() {

    data class Result(val movies: JSONArray, val ratings: JSONArray)

    class GetDataTask : AsyncTask<Void, Void, Result>() {

        private var innerMovies : JSONArray = JSONArray()
        private var innerRatings : JSONArray = JSONArray()

        override fun doInBackground(vararg params: Void?): Result? {
            val response: Response = khttpGet("https://pure-river-78957.herokuapp.com/movies")
            innerMovies = response.jsonArray

            for (i in 0 until innerMovies.length()) {
                val kinopoiskId = innerMovies.getJSONObject(i).get("kinopoisk_id").toString()
                val ratingsById = khttpGet("https://rating.kinopoisk.ru/${kinopoiskId}.xml")
                innerRatings.put(XML.toJSONObject(ratingsById.text))
            }

            return Result(innerMovies, innerRatings)
        }
    }

    private var movies : JSONArray = JSONArray()
    private var ratings : JSONArray = JSONArray()

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment.newInstance(movies.toString())
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_ratings -> {
                val ratingsFragment = RatingsFragment.newInstance(movies.toString(), ratings.toString())
                openFragment(ratingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loadingFragment = LoadingFragment.newInstance()
        openFragment(loadingFragment)

        try {
            val data = GetDataTask().execute().get()
            movies = data.movies
            ratings = data.ratings
            val homeFragment = HomeFragment.newInstance(movies.toString())
            openFragment(homeFragment)
        } catch (e: Exception) {
            // TODO: error handling
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
