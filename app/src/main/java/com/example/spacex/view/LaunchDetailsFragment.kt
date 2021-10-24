package com.example.spacex.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.spacex.R
import com.example.spacex.databinding.FragmentLaunchdetailsBinding
import com.example.spacex.model.Constants
import com.example.spacex.viewmodel.LaunchDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
@AndroidEntryPoint
class LaunchDetailsFragment : Fragment(R.layout.fragment_launchdetails) {

    private val viewModel by lazy {
        ViewModelProvider(this)[LaunchDetailsViewModel::class.java]
    }
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    private var images = mutableListOf<String>()
    private var _binding: FragmentLaunchdetailsBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val launchId = requireArguments().getString(Constants.LAUNCHID)!!
        _binding = FragmentLaunchdetailsBinding.bind(view)
        viewModel.getDetails(launchId)
        setupBindings()
    }

    @SuppressLint("SetTextI18n")
    private fun setupBindings() {
        viewModel.data.observe(viewLifecycleOwner) {
            binding.textview.text = "${requireContext().getString(R.string.mission_label)} ${it.mission_name}\n\n" +
                    "${requireContext().getString(R.string.launch_site)} ${it.launch_site!!.site_name}\n\n" +
                    "${requireContext().getString(R.string.success)} ${it.launch_success}\n\n" +
                    "${requireContext().getString(R.string.rocket_name)} ${it.rocket!!.rocket_name}\n\n" +
                    "${requireContext().getString(R.string.date_label)} ${
                        OffsetDateTime.parse(it.launch_date_local.toString()).format(formatter)
                    }\n\n" +
                    "${requireContext().getString(R.string.description)}\n${it.details}"

            it.links?.flickr_images?.forEach { link ->
                images.add(link!!)
            }

            binding.carousel.pageCount = images.size
            if (images.isEmpty()) {
                binding.carousel.visibility = View.GONE
                binding.textview.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            binding.textview.movementMethod = ScrollingMovementMethod()
        }
        binding.carousel.setImageListener { position, imageView ->
            Glide.with(requireContext())
                .load(images[position])
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .error(android.R.drawable.stat_notify_error)
                .into(imageView)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}