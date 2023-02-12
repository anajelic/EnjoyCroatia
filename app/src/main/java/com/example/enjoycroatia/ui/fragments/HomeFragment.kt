package com.example.enjoycroatia.ui.fragments

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.ui.adapters.DestinationsRecyclerViewAdapter
import com.example.enjoycroatia.ui.dialogs.DestinationDetailsDialog
import com.example.enjoycroatia.viewmodels.DestinationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: DestinationsViewModel by viewModels()

    private var destinationDetailsPopUp: PopupWindow? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteDestinations()
        viewModel.refreshDestinations()
        val adapter = DestinationsRecyclerViewAdapter(object :
            DestinationsRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(item: Destination?) {
                item?.let {
                    showDestinationDetailsPopUp(view, item)
                }
            }
        })

        rvDestinations.adapter = adapter

        viewModel.destinations.observe(viewLifecycleOwner) {
            it?.let {
                adapter.data = it
            }
        }
    }

    private fun showDestinationDetailsPopUp(view: View, destination: Destination) {
        destinationDetailsPopUp =
            DestinationDetailsDialog(
                view,
                layoutInflater,
                destination,
                viewModel.favoriteDestinations.value
            ).displayPopup(
                Gravity.TOP
            )
    }

}