package com.example.enjoycroatia.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.ui.adapters.FavoritesRecyclerViewAdapter
import com.example.enjoycroatia.viewmodels.DestinationsViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.text.SimpleDateFormat
import java.util.*


class FavoriteFragment : Fragment() {

    private val viewModel: DestinationsViewModel by viewModels()

    var cal: Calendar = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUser()

        viewModel.getFavoriteDestinations()

        val adapter = FavoritesRecyclerViewAdapter(object :
            FavoritesRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(item: Destination?) {
                item?.let {
                    setupDatePickerDialog(item)
                }
            }
        })


        rvDestinationsFavorite.adapter = adapter

        viewModel.favoriteDestinations.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
            }
        }

        viewModel.userLiveData.observe(viewLifecycleOwner) {
            it?.let {
                adapter.usersFavorites = it.favoriteDestinations
            }
        }

    }

    private fun setupDatePickerDialog(destination: Destination) {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateVisitedDate(destination)
            }

        DatePickerDialog(
            requireContext(),
            dateSetListener,
            // set DatePickerDialog to point to today's date when it loads up
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun updateVisitedDate(destination: Destination) {
        val myFormat = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        viewModel.setVisitedDate(destination, sdf.format(cal.time))

    }
}