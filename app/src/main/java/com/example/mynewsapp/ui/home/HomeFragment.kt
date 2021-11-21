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
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentHomeBinding
import com.example.mynewsapp.news.News
import com.example.mynewsapp.news.NewsAdapter
import com.example.mynewsapp.news.NewsApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {

    private var columnCount = 1
    private var binding: FragmentHomeBinding? = null
    lateinit var newsSearch: String

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val startSearch: String = "Pokemon"
        val wordSearch = binding?.userSearch

        binding?.buttonSearch?.setOnClickListener{
            if (wordSearch?.text?.toString()?.trim() != ""){
                newsSearch = wordSearch?.text?.toString()?.trim().toString()
                findThisNews(newsSearch)
            }
        }

        findThisNews(startSearch)

        return binding?.root
    }

    @SuppressLint("CheckResult")
    fun findThisNews(newsSearch: String) {

        val getCoolNews = NewsApiClient.apiClient.getTopRatedMovies(newsSearch, API_KEY)

        getCoolNews
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { it ->
                    val myNews = it.articles

                    with(binding?.list) {
                        this?.layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        this?.adapter = context?.let { it1 ->
                            NewsAdapter(myNews, R.layout.list_item_news,
                                it1,::navigateToSingleNew)
                        }
                    }
                },
                { error ->
                    Log.e(TAG, error.toString())
                })
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