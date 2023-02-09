package com.hankim.smokingarea.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SearchData
import com.hankim.smokingarea.SmokingList
import com.hankim.smokingarea.databinding.FragmentSmokersBinding
import com.hankim.smokingarea.network.ApiClient
import com.hankim.smokingarea.smokers.SmokersListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SmokersFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private var recyclerAdapter = SmokersListAdapter()
    private var _binding: FragmentSmokersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSmokersBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.activity)

        recyclerAdapter.setOnClickListener(object : SmokersListAdapter.OnClickListener {
            override fun onClick(position: Int, model: SmokingList) {
                findNavController().navigate(R.id.action_searchFragment_to_smokersDetailFragment)
            }
        })

        getSmokingListFromAPI()

    }

    private fun getSmokingListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.firebase_url))
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
