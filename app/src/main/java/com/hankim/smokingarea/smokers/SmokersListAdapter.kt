package com.hankim.smokingarea.smokers


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SmokingList


class SmokersListAdapter :
    ListAdapter<SmokingList, SmokersListAdapter.SmokersListViewHolder>(differ) {

    private var onClickListener: OnClickListener? = null

    inner class SmokersListViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        fun bind(smokingList: SmokingList) {
            val bannerPlaceNameTextView = view.findViewById<TextView>(R.id.tv_place_name)
            val bannerPlaceAddressTextView =
                view.findViewById<TextView>(R.id.tv_place_address)

            bannerPlaceNameTextView.text = smokingList.place
            bannerPlaceAddressTextView.text = smokingList.address

        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmokersListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SmokersListViewHolder(inflater.inflate(R.layout.item_smokers_list, parent, false))

    }

    override fun onBindViewHolder(holder: SmokersListViewHolder, position: Int) {
        holder.bind(currentList[position])

        holder.itemView.setOnClickListener {
            if(onClickListener != null) {
                onClickListener!!.onClick(position, currentList[position])
            }
        }
    }

    interface OnClickListener {
        fun  onClick(position: Int, model: SmokingList)
    }


    companion object {
        val differ = object : DiffUtil.ItemCallback<SmokingList>() {
            override fun areItemsTheSame(oldItem: SmokingList, newItem: SmokingList): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: SmokingList,
                newItem: SmokingList
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}



