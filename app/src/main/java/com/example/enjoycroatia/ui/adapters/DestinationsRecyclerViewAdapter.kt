package com.example.enjoycroatia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.databinding.LayoutDestinationItemBinding

class DestinationsRecyclerViewAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<DestinationsRecyclerViewAdapter.DestinationsViewHolder>() {

    var data: List<Destination> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(item: Destination?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationsViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_destination_item, parent, false)
        val layoutInflater = LayoutInflater.from(view.context)
        val binding =
            LayoutDestinationItemBinding.inflate(layoutInflater, view as ViewGroup?, false)
        return DestinationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinationsViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class DestinationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var binding: LayoutDestinationItemBinding

        constructor(binding: LayoutDestinationItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(item: Destination, listener: OnItemClickListener?) {

            binding.destination = item
            binding.ivImage.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }
}