package com.example.enjoycroatia.ui.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.databinding.DestinationDetailsDialogBinding
import com.example.enjoycroatia.util.FirebaseStorageManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DestinationDetailsDialog(
    val contentFrame: View?,
    val layoutInflater: LayoutInflater,
    val destination: Destination,
    val favorites: List<Destination>?
) {

    fun displayPopup(gravity: Int): PopupWindow? {
        try {
            val binding = DataBindingUtil.inflate<DestinationDetailsDialogBinding>(
                layoutInflater,
                R.layout.destination_details_dialog,
                null,
                false
            )

            val popUpView = binding.root

            val mpopup = PopupWindow(
                popUpView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            binding.destination = destination

            favorites?.forEach {
                if (it.id == destination.id) {
                    showRemoveIcon(popUpView)
                }
            }


            popUpView.findViewById<Button>(R.id.btnClose)?.setOnClickListener {
                mpopup.dismiss()
            }

            popUpView.findViewById<TextView>(R.id.tvLocationTitle)?.setOnClickListener {
                showGoogleMapsLocation(it.context, destination)
            }

            popUpView.findViewById<ImageView>(R.id.ivAddFavorite)?.setOnClickListener {
                GlobalScope.launch {
                    FirebaseStorageManager.addFavorite(destination)
                }
                showRemoveIcon(popUpView)
                Toast.makeText(it.context, "Added to favorites!", Toast.LENGTH_SHORT).show()
            }

            popUpView.findViewById<ImageView>(R.id.ivRemoveFavorite)?.setOnClickListener {
                GlobalScope.launch {
                    FirebaseStorageManager.removeFavorite(destination)
                }
                showAddIcon(popUpView)
                Toast.makeText(it.context, "Removed from favorites!", Toast.LENGTH_SHORT).show()
            }

            //
            mpopup.showAtLocation(contentFrame, gravity, 0, 0)

            mpopup.isOutsideTouchable = false
            mpopup.isFocusable = false
            mpopup.update()
            return mpopup
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun showAddIcon(view: View) {
        view.findViewById<ImageView>(R.id.ivAddFavorite)?.visibility = View.VISIBLE
        view.findViewById<ImageView>(R.id.ivRemoveFavorite)?.visibility = View.GONE

    }

    private fun showRemoveIcon(view: View) {
        view.findViewById<ImageView>(R.id.ivAddFavorite)?.visibility = View.GONE
        view.findViewById<ImageView>(R.id.ivRemoveFavorite)?.visibility = View.VISIBLE
    }

    private fun showGoogleMapsLocation(context: Context, destination: Destination) {
        val gmmIntentUri =
            Uri.parse("geo:0,0?q=${destination.title}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(context, mapIntent, null)
    }
}