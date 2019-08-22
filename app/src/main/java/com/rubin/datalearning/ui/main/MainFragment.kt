package com.rubin.datalearning.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rubin.datalearning.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.monsterData.observe(this, Observer {
            val monsterName = StringBuilder()
            for (monster in it) {
                monsterName.append(monster.monsterName).append("\n")
            }

            message.text = monsterName
        })

        return inflater.inflate(R.layout.main_fragment, container, false)
    }
}