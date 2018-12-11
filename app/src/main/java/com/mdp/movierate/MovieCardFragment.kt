package com.mdp.movierate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONObject

class MovieCardFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movie = JSONObject(arguments!!.getString("movie"))
        println(movie)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_movie_card, container, false)

    companion object {
        fun newInstance(movie: String): MovieCardFragment {
            val args = Bundle()
            args.putString("movie", movie)
            val fragment = MovieCardFragment()
            fragment.arguments = args

            return fragment
        }
    }
}