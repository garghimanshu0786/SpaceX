package com.example.spacex.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.spacex.LaunchListQuery
import com.example.spacex.R
import com.example.spacex.databinding.FragmentLaunchlistBinding
import com.example.spacex.viewmodel.LaunchListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LaunchListFragment : Fragment(R.layout.fragment_launchlist) {
    private val viewModel : LaunchListViewModel by lazy {
        ViewModelProvider(this)[LaunchListViewModel::class.java]
    }
    private var launches: ArrayList<LaunchListQuery.Launch> = ArrayList()
    private val adapter = LaunchListAdapter(this, launches)
    private var _binding: FragmentLaunchlistBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLaunchlistBinding.bind(view)
        setupAdapter()
        setupSpinners()
    }

    private fun setupAdapter() {
        viewModel.data.observe(viewLifecycleOwner) {
            launches.addAll(it)
            adapter.notifyDataSetChanged()
        }
        binding.recyclerview.apply {
            this.adapter = this@LaunchListFragment.adapter
            this.setHasFixedSize(true)
        }
    }

    private fun setupSpinners() {
        val itemsSpinner = arrayOf(getString(R.string.sort_asc), getString(R.string.sort_desc))
        binding.missionSpinner.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                itemsSpinner
            )
        )
        binding.missionSpinner.onItemClickListener = AdapterView.OnItemClickListener { _, _, p2, _ ->
            if (p2 == 0)
                adapter.sortByMissionNameAscending()
            else if (p2 == 1)
                adapter.sortByMissionNameDescending()
            binding.missionSpinner.dismissDropDown()
        }
        binding.launchDateSpinner.setAdapter(
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_list_item_1,
                itemsSpinner
            )
        )
        binding.launchDateSpinner.onItemClickListener = AdapterView.OnItemClickListener { _, _, p2, _ ->
            if (p2 == 0)
                adapter.sortByLaunchDateAscending()
            else if (p2 == 1)
                adapter.sortByLaunchDateDescending()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
