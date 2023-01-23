package com.hankim.smokingarea

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class SearchBannerAdapter :
    ListAdapter<Banner, SearchBannerAdapter.SearchBannerViewHolder>(BannerDIffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBannerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_banner, parent, false)
        return SearchBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SearchBannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bannerView = view.findViewById<View>(R.id.v_banner)
        private val bannerPlaceNameTextView = view.findViewById<TextView>(R.id.tv_place_name)
        private val bannerPlaceAddressTextView = view.findViewById<TextView>(R.id.tv_place_address)
        private val bannerPlaceSearchTextView = view.findViewById<TextView>(R.id.tv_place_search)



        fun bind(banner: Banner) {
            bannerPlaceNameTextView.text = banner.place
            bannerPlaceAddressTextView.text = banner.address


        }
    }
}

class BannerDIffCallback : DiffUtil.ItemCallback<Banner>() {
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.address == newItem.address
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}