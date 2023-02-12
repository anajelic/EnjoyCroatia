package com.example.enjoycroatia.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.enjoycroatia.R
import com.example.enjoycroatia.data.Destination
import com.example.enjoycroatia.databinding.LayoutFavoritesItemBinding

class FavoritesRecyclerViewAdapter(val listener: OnItemClickListener?) :
    RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder>() {

    var data: List<Destination> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var usersFavorites: Map<String, String> = mapOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    interface OnItemClickListener {
        fun onItemClick(item: Destination?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_favorites_item, parent, false)
        val layoutInflater = LayoutInflater.from(view.context)
        val binding =
            LayoutFavoritesItemBinding.inflate(layoutInflater, view as ViewGroup?, false)
        return FavoritesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(data[position], usersFavorites, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var binding: LayoutFavoritesItemBinding

        constructor(binding: LayoutFavoritesItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(
            item: Destination,
            userFavorites: Map<String, String>,
            listener: OnItemClickListener?
        ) {
            binding.destination = item

            userFavorites.entries.forEach {
                if (item.id == it.key) {
                    binding.tvLastVisited.text = it.value
                }
            }

            binding.ivImage.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }
}