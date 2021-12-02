package com.example.mynewsapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
    private var pageSearch: Int = 1
    private var wordFull: Boolean = false
    private var pageFull: Boolean = false
    private var wordpageFull: Boolean = false
    private var wordpageBlank: Boolean = false

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        val startSearch: String = "Lego"
        val wordSearch = binding?.userSearch
        val startPage: Int = 1
        val userPage = binding?.userNumber

        binding?.buttonSearch?.setOnClickListener{
            newsPages(wordSearch, userPage)
            when {
                wordFull -> {
                    newsSearch = wordSearch?.text?.toString()?.trim().toString()
                    findThisNews(newsSearch, startPage)
                }
                pageFull -> {
                    pageSearch = userPage?.text.toString().toInt()
                    findThisNews(startSearch, pageSearch)
                }
                wordpageFull -> {
                    newsSearch = wordSearch?.text?.toString()?.trim().toString()
                    pageSearch = userPage?.text.toString().toInt()
                    findThisNews(newsSearch, pageSearch)
                }
                wordpageBlank -> {
                    findThisNews(startSearch, startPage)
                }
            }
        }
        findThisNews(startSearch, startPage)
        return binding?.root
    }

    @SuppressLint("CheckResult")
    fun findThisNews(newsSearch: String, pageSearch: Int) {

        val getCoolNews = NewsApiClient.apiClient.getTopNews(newsSearch, API_KEY,LANGUAGE_KEY, pageSearch)

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

    private fun navigateToSingleNew(news: News) {
        val bundle = Bundle()
        bundle.putString("news", news.url)
        findNavController().navigate(R.id.action_navigation_home_to_newsFragment, bundle)
    }

    private fun newsPages(wordSearch: EditText?, userPage: EditText?) {
        wordFull = wordSearch?.text?.toString()?.trim() != "" && userPage?.text?.toString() == ""
        pageFull = wordSearch?.text?.toString()?.trim() == "" && userPage?.text?.toString() != ""
        wordpageFull = wordSearch?.text?.toString()?.trim() != "" && userPage?.text?.toString() != ""
        wordpageBlank = wordSearch?.text?.toString()?.trim() == "" && userPage?.text?.toString() == ""
    }

    companion object {
        private val TAG = HomeFragment::class.java.simpleName
        private const val API_KEY = "4e9df8dd3f724dd780844ff8cb17d203"
        private const val LANGUAGE_KEY = "ru"
    }
}