package com.mdp.movierate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONArray

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val movies = JSONArray(arguments!!.getString("movies"))
        generateCards(movies)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    companion object {
        fun newInstance(movies: String): HomeFragment {
            val args = Bundle()
            args.putString("movies", movies)
            val fragment = HomeFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private fun addFragment(fragment: Fragment) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.home_layout, fragment).commit()
    }

    private fun generateCards(data: JSONArray) {
        for(i in 0 until data.length()) {
            val item = data.getJSONObject(i)
            addFragment(MovieCardFragment.newInstance(item.toString()))
        }
    }
}