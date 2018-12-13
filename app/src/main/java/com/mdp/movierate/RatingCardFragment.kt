package com.mdp.movierate

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_rating_card.*
import org.json.JSONObject
import khttp.get as khttpGet

class RatingCardFragment : Fragment() {

    private var movieData : JSONObject = JSONObject()
    private var ratingData: JSONObject = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieData = JSONObject(arguments!!.getString("movie"))
        ratingData = JSONObject(arguments!!.getString("rating"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_rating_card, container, false)


    companion object {
        fun newInstance(movie: String, rating: String): RatingCardFragment {
            val args = Bundle()
            args.putString("movie", movie)
            args.putString("rating", rating)
            val fragment = RatingCardFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onStart() {
        super.onStart()

        formCard()
    }

    private fun formCard() {
        rating_title.text = movieData.get("title").toString()

        val kpRating = ratingData.getJSONObject("rating").getJSONObject("kp_rating").getString("content")
        val imdbRating = ratingData.getJSONObject("rating").getJSONObject("imdb_rating").getString("content")

        kp_rating.text = kpRating
        imdb_rating.text = imdbRating
        kp_rating.setTextColor(ratingColor(kpRating.toDouble()))
        imdb_rating.setTextColor(ratingColor(imdbRating.toDouble()))
    }

    private fun ratingColor(rating: Double): Int {
        when(rating.toInt()) {
            in 7..10 -> return Color.parseColor("#2e7d32")
            in 1..4 -> return Color.parseColor("#b71c1c")
            else -> return Color.DKGRAY
        }
    }
}