package com.rubin.datalearning.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rubin.datalearning.R
import com.rubin.datalearning.data.Monster
import com.rubin.datalearning.ui.shared.SharedViewModel
import com.rubin.datalearning.utilities.LOG_TAG
import com.rubin.datalearning.utilities.PrefsHelper
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment(), MainRecyclerAdapter.MonsterItemListener {

    private lateinit var viewModel: SharedViewModel
    private lateinit var navController: NavController
    private lateinit var adapter: MainRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).run {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        val layoutStyle = PrefsHelper.getItemType(requireContext())
        view.recyclerView.layoutManager = if (layoutStyle == "grid") GridLayoutManager(
            requireContext(),
            2
        ) else LinearLayoutManager(requireContext())

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host)

        view.swipeRefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        setHasOptionsMenu(true)

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        viewModel.monsterData.observe(this, Observer {
            adapter = MainRecyclerAdapter(requireContext(), it, this@MainFragment)
            view.recyclerView.adapter = adapter
            view.swipeRefresh.isRefreshing = false
        })

        viewModel.activityTitle.observe(this, Observer {
            requireActivity().title = it
        })

        return view
    }

    override fun onMonsterItemClicked(monster: Monster) {
        Log.i(LOG_TAG, "Selected Monster: ${monster.monsterName}")
        viewModel.selectedMonster.value = monster
        navController.navigate(R.id.action_nav_detail)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_view_grid -> {
                PrefsHelper.setItemType(requireContext(), "grid")
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
                recyclerView.adapter = adapter
            }
            R.id.action_view_list -> {
                PrefsHelper.setItemType(requireContext(), "list")
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = adapter
            }
            R.id.action_settings -> {
                navController.navigate(R.id.settingsActivity)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateActivityTitle()
    }
}