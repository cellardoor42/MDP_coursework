package com.mdp.movierate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.design.widget.BottomNavigationView.*
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.mongodb.client.MongoClients


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_ratings -> {
                val ratingsFragment = RatingsFragment.newInstance()
                openFragment(ratingsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val client = MongoClients.create("mongodb://dbuser:inttechdb@ds111103.mlab.com:11103/int_tech")
        val db = client.getDatabase("int_tech")
        val movies = db.getCollection("movies")
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
