package com.rubin.datalearning.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rubin.datalearning.R
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        view.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.monsterData.observe(this, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it)
            view.recyclerView.adapter = adapter
            view.swipeRefresh.isRefreshing = false
        })

        return view
    }
}