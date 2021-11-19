package com.example.mynewsapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynewsapp.R
import com.example.mynewsapp.news.News
import com.example.mynewsapp.news.NewsAdapter
import com.example.mynewsapp.news.NewsApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    private var columnCount = 1

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val getTopRatedMovies = NewsApiClient.apiClient.getTopRatedMovies("Pokemon", API_KEY)

        getTopRatedMovies
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    val movies = it.articles
                    if (view is RecyclerView) {
                        with(view) {
                            layoutManager = when {
                                columnCount <= 1 -> LinearLayoutManager(context)
                                else -> GridLayoutManager(context, columnCount)
                            }
                            adapter = NewsAdapter(movies, R.layout.list_item_news,context,::navigateToSingleNew)
                        }
                    }
                },
                { error ->
                    Log.e(TAG, error.toString())
                }
            )
        return view
    }

    fun navigateToSingleNew(news: News) {
        val bundle = Bundle()
        bundle.putString("news", news.url)
        findNavController().navigate(R.id.action_navigation_home_to_newsFragment, bundle)
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
        private const val API_KEY = "4e9df8dd3f724dd780844ff8cb17d203"
    }
}