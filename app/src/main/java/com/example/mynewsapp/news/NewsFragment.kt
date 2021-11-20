package com.example.mynewsapp.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater,container,false)
        val urlWeb = arguments?.getString("news")
        val mWebView = binding?.webview as WebView
        mWebView.loadUrl("$urlWeb")
        val webSettings = mWebView.getSettings()
        webSettings.setJavaScriptEnabled(true)
        mWebView.webViewClient = WebViewClient()
        return binding?.root
    }
}