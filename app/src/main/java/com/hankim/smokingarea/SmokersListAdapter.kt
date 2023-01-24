package com.hankim.smokingarea

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class SmokersListAdapter :
    ListAdapter<SmokingList, SmokersListAdapter.HomeBannerViewHolder>(differ) {

    inner class HomeBannerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(smokingList: SmokingList) {
            val bannerPlaceNameTextView = view.findViewById<TextView>(R.id.tv_place_name)
            val bannerPlaceAddressTextView =
                view.findViewById<TextView>(R.id.tv_place_address)

            bannerPlaceNameTextView.text = smokingList.place
            bannerPlaceAddressTextView.text = smokingList.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HomeBannerViewHolder(inflater.inflate(R.layout.item_smokers_list, parent, false))
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<SmokingList>() {
            override fun areItemsTheSame(oldItem: SmokingList, newItem: SmokingList): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SmokingList, newItem: SmokingList): Boolean {
                return oldItem == newItem
            }
        }
    }
}



