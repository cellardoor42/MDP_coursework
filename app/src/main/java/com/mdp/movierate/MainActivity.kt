package com.mdp.movierate

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.design.widget.BottomNavigationView.*
import android.support.v7.app.AppCompatActivity
import khttp.responses.Response
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import khttp.get as khttpGet

class MainActivity : AppCompatActivity() {

    class GetDataTask : AsyncTask<Void, Void, JSONArray>() {

        override fun doInBackground(vararg params: Void?): JSONArray? {
            val response : Response = khttpGet("https://pure-river-78957.herokuapp.com/movies")

            return response.jsonArray
        }
    }

    private var movies : JSONArray = JSONArray()

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment.newInstance(movies.toString())
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_ratings -> {
                val ratingsFragment = RatingsFragment.newInstance(movies.toString())
                openFragment(ratingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movies = GetDataTask().execute().get()

        val homeFragment = HomeFragment.newInstance(movies.toString())
        openFragment(homeFragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
