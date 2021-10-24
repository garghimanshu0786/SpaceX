package com.example.spacex.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.spacex.LaunchListQuery
import com.example.spacex.R
import com.example.spacex.databinding.CardViewDesignBinding
import com.example.spacex.model.Constants
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class LaunchListAdapter(
    private val fragment: Fragment,
    private val launches: ArrayList<LaunchListQuery.Launch>
) : RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {

    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")

    override fun getItemCount(): Int {
        return launches.size
    }

    inner class ViewHolder(private val binding: CardViewDesignBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(launch: LaunchListQuery.Launch) {
            binding.textView.text =
                "${
                    fragment.requireContext().getString(R.string.mission_label)
                } ${
                    launch.mission_name
                }\n${
                    fragment.requireContext().getString(R.string.date_label)
                } ${
                    OffsetDateTime.parse(launch.launch_date_local.toString()).format(formatter)
                }"
            Glide.with(fragment.requireContext())
                .load(launch.links?.mission_patch_small)
                .error(R.drawable.placeholder)
                .into(binding.imageView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launches[position]
        holder.bind(launch)
        holder.itemView.setOnClickListener {
            LaunchDetailsFragment().apply {
                arguments = Bundle().apply { putString(Constants.LAUNCHID, launch.id) }
            }.also {
                fragment.requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CardViewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun sortByMissionNameAscending() {
        launches.sortBy { it.mission_name }
        notifyDataSetChanged()
    }

    fun sortByMissionNameDescending() {
        launches.sortByDescending { it.mission_name }
        notifyDataSetChanged()
    }

    fun sortByLaunchDateDescending() {
        launches.sortByDescending { OffsetDateTime.parse(it.launch_date_local.toString()) }
        notifyDataSetChanged()
    }

    fun sortByLaunchDateAscending() {
        launches.sortBy { OffsetDateTime.parse(it.launch_date_local.toString()) }
        notifyDataSetChanged()
    }
}