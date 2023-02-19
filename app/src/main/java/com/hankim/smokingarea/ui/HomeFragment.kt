package com.hankim.smokingarea.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hankim.smokingarea.R
import com.hankim.smokingarea.database.SmokersEntity
import com.hankim.smokingarea.network.SmokersDto
import com.hankim.smokingarea.databinding.FragmentHomeBinding
import com.hankim.smokingarea.home.AddSmokersActivity
import com.hankim.smokingarea.network.SmokersService
import com.hankim.smokingarea.viewmodels.SmokersViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.LocationButtonView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), OnMapReadyCallback, Overlay.OnClickListener {

    private val viewModel: SmokersViewModel by lazy {
        val activity = requireNotNull(this.activity) {

        }
        ViewModelProvider(this, SmokersViewModel.Factory(activity.application))[SmokersViewModel::class.java]
    }

    private lateinit var viewPagerAdapter: HomeBannerAdapter
    private lateinit var binding: FragmentHomeBinding

    // NaverMap
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var currentLocationButton: LocationButtonView

    // Viewpager2
    private lateinit var viewPager: ViewPager2


    //Firebase
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 맵뷰 구성
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // 뷰페이저 구성
        viewPager = binding.homeViewPager
        viewPager.adapter = viewPagerAdapter

        // 뷰페이저 클릭 시 마커 이동 구현
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                val selectedSmokingList = viewPagerAdapter.currentList[position]
                val cameraUpdate =
                    CameraUpdate.scrollTo(LatLng(selectedSmokingList.lat, selectedSmokingList.lng))
                        .animate(CameraAnimation.Easing)

                naverMap.moveCamera(cameraUpdate)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // NaverMap 위치 설정
        currentLocationButton = binding.btCurrentLocation
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        setFloatingButtonClickEvent()
    }

    override fun onMapReady(map: NaverMap) {
        this.naverMap = map
        map.locationSource = locationSource

        map.maxZoom = 18.0
        map.minZoom = 10.0

        currentLocationButton.map = naverMap

        val uiSetting = map.uiSettings
        uiSetting.isLocationButtonEnabled = false

        map.locationTrackingMode = LocationTrackingMode.Follow

        getSmokingListFromAPI()
        setOffScreenViewPager()

    }

    private fun getSmokingListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl(getString(R.string.firebase_url))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(SmokersService::class.java)
            .also {
                it.getSmokersPlace()
                    .enqueue(object : Callback<SmokersDto> {
                        override fun onResponse(
                            call: Call<SmokersDto>,
                            response: Response<SmokersDto>
                        ) {
                            if (response.isSuccessful.not()) {
                                return
                            }

                            response.body()?.let { data ->
                                updateMarker(data.smokingList)
                                viewPagerAdapter.submitList(data.smokingList)
                            }

                        }

                        override fun onFailure(call: Call<SmokersDto>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
            }
    }

    private fun setOffScreenViewPager() {

        val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
        val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
        val screenWidth = resources.displayMetrics.widthPixels
        val offset = screenWidth - pageWidth - pageMargin

        viewPager.offscreenPageLimit = 3
        viewPager.setPageTransformer { page, position ->
            page.translationX = position * -offset
        }
    }

    private fun updateMarker(dataLists: List<SmokersEntity>) {
        dataLists.forEach { dataList ->
            val marker = Marker()
            marker.position = LatLng(dataList.lat, dataList.lng)
            marker.onClickListener = this
            marker.map = naverMap
            marker.tag = dataList.id
            marker.icon = OverlayImage.fromResource(R.drawable.ic_smoking_marker)

        }
    }

    private fun setFloatingButtonClickEvent() {
        binding.addFloatingButton.setOnClickListener {
            context?.let {
                //                if (auth.currentUser != null) {
                val intent = Intent(requireContext(), AddSmokersActivity::class.java)
                startActivity(intent)
                //                } else {
                //                    Snackbar.make(view, "로그인 후 사용해주세요", Snackbar.LENGTH_LONG).show()
                //                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onClick(overlay: Overlay): Boolean {
        overlay.tag

        val selectedModel = viewPagerAdapter.currentList.firstOrNull {
            it.id == overlay.tag
        }

        selectedModel?.let {
            val position = viewPagerAdapter.currentList.indexOf(it)
            viewPager.currentItem = position
        }

        return true
    }


}


