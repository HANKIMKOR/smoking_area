package com.hankim.smokingarea.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SmokingList
import com.hankim.smokingarea.databinding.ItemHomeBannerBinding


class HomeBannerAdapter :
    ListAdapter<SmokingList, HomeBannerAdapter.HomeBannerViewHolder>(BannerDiffCallback()) {

    private lateinit var binding: ItemHomeBannerBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeBannerViewHolder {
        binding = ItemHomeBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeBannerViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HomeBannerViewHolder(private val binding: ItemHomeBannerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(smokingList: SmokingList) {
            binding.viewModel = viewModel

            val bannerPlaceNameTextView = view.findViewById<TextView>(R.id.tv_place_name)
            val bannerPlaceAddressTextView =
                view.findViewById<TextView>(R.id.tv_place_address)

            bannerPlaceNameTextView.text = smokingList.place
            bannerPlaceAddressTextView.text = smokingList.address
        }
    }
}

class BannerDiffCallback : DiffUtil.ItemCallback<SmokingList>() {
    override fun areItemsTheSame(oldItem: SmokingList, newItem: SmokingList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SmokingList, newItem: SmokingList): Boolean {
        return oldItem == newItem
    }
}




