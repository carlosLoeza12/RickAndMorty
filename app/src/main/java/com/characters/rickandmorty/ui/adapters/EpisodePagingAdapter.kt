package com.characters.rickandmorty.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.characters.rickandmorty.R
import com.characters.rickandmorty.data.model.Episode
import com.characters.rickandmorty.databinding.LocationListItemBinding

class EpisodePagingAdapter : PagingDataAdapter<Episode, EpisodePagingAdapter.ViewHolder>(diffCallback){

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Episode>(){
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Episode? = getItem(position)
        item?.let {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = LocationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, parent.context)
    }

    inner class ViewHolder(private val binding: LocationListItemBinding, private val context: Context): RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode){
            binding.txtOriginName.text = context.getString(R.string.format_location_name, episode.name)
            binding.txtOriginType.text = context.getString(R.string.format_episode_air_date, episode.air_date)
            binding.txtOriginDimension.text = context.getString(R.string.format_episode, episode.episode)
            binding.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_item_from_left))
        }
    }
}