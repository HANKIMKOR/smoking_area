package com.hankim.smokingarea

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val assetLoader = AssetLoader()
        val areaData = assetLoader.getJsonString(requireContext(), "area_list.json")

        if (!areaData.isNullOrEmpty()) {
            val jsonObject = JSONObject(areaData)
            val list = jsonObject.getJSONArray("list")
            val firstList = list.getJSONObject(0)

            val local1 = firstList.getString("local1")
            val local2 = firstList.getString("local2")
            val place = firstList.getString("place")
            val address = firstList.getString("address")
            val sector = firstList.getString("sector")
            val divide1 = firstList.getString("divide1")
            val divide2 = firstList.getString("divide2")
            val update = firstList.getString("update")

            val smokingListValue = com.hankim.smokingarea.SmokingList(
                local1,
                local2,
                place,
                address,
                sector,
                divide1,
                divide2,
                update
            )
            smokingListValue.local1

            Log.d("place_detail", smokingListValue.local2)

        }


    }
}