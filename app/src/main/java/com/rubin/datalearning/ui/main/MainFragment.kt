package com.rubin.datalearning.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.rubin.datalearning.R
import com.rubin.datalearning.data.Monster
import com.rubin.datalearning.ui.shared.SharedViewModel
import com.rubin.datalearning.utilities.LOG_TAG
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment(), MainRecyclerAdapter.MonsterItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

        view.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModel.monsterData.observe(this, Observer {
            val adapter = MainRecyclerAdapter(requireContext(), it, this@MainFragment)
            view.recyclerView.adapter = adapter
            view.swipeRefresh.isRefreshing = false
        })

        return view
    }

    override fun onMonsterItemClicked(monster: Monster) {
        Log.i(LOG_TAG, "Selected Monster: ${monster.monsterName}")
        viewModel.selectedMonster.value = monster
        navController.navigate(R.id.action_nav_detail)
    }
}