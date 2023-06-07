package com.characters.rickandmorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.characters.rickandmorty.R
import com.characters.rickandmorty.data.model.Location
import com.characters.rickandmorty.databinding.LocationListItemBinding

class LocationPagingAdapter : PagingDataAdapter<Location, LocationPagingAdapter.ViewHolder>(diffCallback) {

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Location>(){
            override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Location? = getItem(position)
        if(item != null) holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, parent.context)
    }

    inner class ViewHolder(private val binding: LocationListItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind(location: Location){
            binding.txtOriginName.text = context.getString(R.string.format_location_name, location.name)
            binding.txtOriginType.text = context.getString(R.string.format_location_type, location.type)
            binding.txtOriginDimension.text = context.getString(R.string.format_location_dimension, location.dimension)
            binding.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_item_from_left))
        }
    }
}