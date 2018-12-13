package com.mdp.movierate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray

class RatingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movies = JSONArray(arguments!!.getString("movies"))
        val ratings = JSONArray(arguments!!.getString("ratings"))
        generateCards(movies, ratings)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_ratings, container, false)

    companion object {
        fun newInstance(movies: String, ratings: String): RatingsFragment {
            val args = Bundle()
            args.putString("movies", movies)
            args.putString("ratings", ratings)
            val fragment = RatingsFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.ratings_layout, fragment).commit()
    }

    private fun generateCards(movies: JSONArray, ratings: JSONArray) {
        for(i in 0 until movies.length()) {
            val movieItem = movies.getJSONObject(i)
            val ratingItem = ratings.getJSONObject(i)
            addFragment(RatingCardFragment.newInstance(movieItem.toString(), ratingItem.toString()))
        }
    }
}