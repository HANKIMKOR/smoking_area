package com.hankim.smokingarea.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SmokingList


class HomeBannerAdapter :
    ListAdapter<SmokingList, HomeBannerAdapter.HomeBannerViewHolder>(differ) {

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
        return HomeBannerViewHolder(inflater.inflate(R.layout.item_home_banner, parent, false))
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



