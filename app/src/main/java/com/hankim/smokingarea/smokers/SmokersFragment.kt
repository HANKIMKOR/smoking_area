package com.hankim.smokingarea.smokers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hankim.smokingarea.SearchData
import com.hankim.smokingarea.databinding.FragmentSmokersBinding
import com.hankim.smokingarea.network.ApiClient
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
        val rootView = binding

        recyclerView = rootView.recyclerView

        return rootView.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.activity)


        getSmokingListFromAPI()
        onClickEvent()

    }

    private fun getSmokingListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://smokingarea-c5d0b-default-rtdb.asia-southeast1.firebasedatabase.app")
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

    private fun onClickEvent() {
//
//        val intent = Intent(this.context,ReportsDetailActivity::class.java)
//        recyclerAdapter.setItemClickListener(object : SmokersListAdapter.OnItemClickListener {
//
//            override fun onClick(view: View, position: Int) {
////                if (view.parent == recyclerView) {
////                    val intent = Intent(context, SmokersDetailActivity::class.java)
////                    intent.putExtra("id", data[recyclerView.getChildAdapterPosition(view)].id)
////                    startActivity(intent)
////                } else {
////                    Log.d("Error", "ddddddd")
////                }
//                Toast.makeText(view.context,
//                    "${data[position].place}\n${data[position].address}",
//                    Toast.LENGTH_SHORT).show()
//            }
//        })
    }

}
