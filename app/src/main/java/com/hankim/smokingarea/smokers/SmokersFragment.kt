package com.hankim.smokingarea.smokers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SearchData
import com.hankim.smokingarea.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SmokersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var recyclerAdapter = SmokersListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_smokers, container, false)

        recyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSmokingListFromAPI()
    }

    private fun getSmokingListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiClient::class.java).also {
            it.getSmokingList()
                .enqueue(object : Callback<SearchData> {
                    override fun onResponse(
                        call: Call<SearchData>,
                        response: Response<SearchData>
                    ) {
                        if (response.isSuccessful.not()) {
                            return
                        }

                        response.body()?.let { data ->
                            recyclerAdapter.submitList(data.smokingList)
                        }
                    }

                    override fun onFailure(call: Call<SearchData>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }
}