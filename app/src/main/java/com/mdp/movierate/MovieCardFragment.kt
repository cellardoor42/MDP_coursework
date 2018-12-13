package com.mdp.movierate

import android.animation.LayoutTransition
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_movie_card.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array

class MovieCardFragment : Fragment() {

    private var movieData : JSONObject = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieData = JSONObject(arguments!!.getString("movie"))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movie_card, container, false)


    override fun onStart() {
        super.onStart()

        formCard()
        card.setOnClickListener {
            expandPlot()
        }

        card.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
    }

    companion object {
        fun newInstance(movie: String): MovieCardFragment {
            val args = Bundle()
            args.putString("movie", movie)
            val fragment = MovieCardFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private fun formCard() {
        val genres: JSONArray = movieData.getJSONArray("genre")
        var genreString = ""
        for(i in 0 until genres.length()) {
            genreString += "${genres[i]}, "
        }

        genreString = genreString.removeSuffix(", ")

        Picasso.get().load(movieData.getString("posterUrl")).into(cover)

        title.text = movieData.getString("title")
        director.text = movieData.getString("director")
        genre.text = genreString
        year.text = movieData.getString("year")
        plot.text = movieData.getString("plot")
        plot.visibility = View.GONE
    }

    private fun expandPlot() {
        if (plot.visibility == View.GONE) {
            plot.visibility = View.VISIBLE
        } else {
            plot.visibility = View.GONE
        }
    }
}