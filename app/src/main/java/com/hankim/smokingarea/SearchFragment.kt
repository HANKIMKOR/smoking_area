package com.hankim.smokingarea

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import org.json.JSONObject

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewpager = view.findViewById<ViewPager2>(R.id.viewpager_search_banner)
        val viewpagerIndicator =
            view.findViewById<TabLayout>(R.id.viewpager_search_banner_indicator)

        val assetLoader = AssetLoader()
        val searchJsonString = assetLoader.getJsonString(requireContext(), "area_list.json")

        if (!searchJsonString.isNullOrEmpty()) {
            val gson = Gson()
            val searchData = gson.fromJson(searchJsonString, SearchData::class.java )
            searchData.list.local1

            viewpager.adapter = SearchBannerAdapter().apply {
                submitList()
            }

        }


    }
}