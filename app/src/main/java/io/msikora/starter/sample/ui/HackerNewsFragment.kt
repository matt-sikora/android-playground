package io.msikora.starter.sample.ui

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import io.msikora.starter.R

class HackerNewsFragment : Fragment() {

    companion object {
        fun newInstance() = HackerNewsFragment()
    }

    private lateinit var viewModel: HackerNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hacker_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HackerNewsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
