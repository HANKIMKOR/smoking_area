package com.hankim.smokingarea.home

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SearchData
import com.hankim.smokingarea.SmokingList
import com.hankim.smokingarea.network.ApiClient
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
    private lateinit var mapView: MapView
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private lateinit var currentLocationButton: LocationButtonView


    // Viewpager2
    private lateinit var viewPager: ViewPager2
    private var viewPagerAdapter = HomeBannerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)


        // 맵뷰 구성
        mapView = rootView.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        currentLocationButton = rootView.findViewById(R.id.bt_CurrentLocation)

        // 뷰페이저 구성
        viewPager = rootView.findViewById(R.id.homeViewPager) as ViewPager2
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


        return rootView
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            naverMap.locationTrackingMode = LocationTrackingMode.Face

        } else {
            naverMap.locationTrackingMode = LocationTrackingMode.None
        }
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        if (locationSource.onRequestPermissionsResult(
//                requestCode, permissions,
//                grantResults
//            )
//        ) {
//            if (!locationSource.isActivated) { // 권한 거부됨
//                naverMap.locationTrackingMode = LocationTrackingMode.None
//            }
//            return
//        }
//        super.request(requestCode, permissions, grantResults)
//    }

    override fun onMapReady(map: NaverMap) {

        requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)

        naverMap = map

        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0

        val uiSetting = naverMap.uiSettings
        uiSetting.isLocationButtonEnabled = false

        currentLocationButton.map = naverMap

        // 위치 추적

        locationSource = FusedLocationSource(
            this@HomeFragment, LOCATION_PERMISSION_REQUEST_CODE
                    )

        naverMap.locationSource = locationSource


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
                            updateMarker(data.smokingList)
                            viewPagerAdapter.submitList(data.smokingList)
                        }
                    }

                    override fun onFailure(call: Call<SearchData>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
        }
    }

    private fun updateMarker(dataLists: List<SmokingList>) {
        dataLists.forEach { dataList ->
            val marker = Marker()
            marker.position = LatLng(dataList.lat, dataList.lng)
            marker.onClickListener = this
            marker.map = naverMap
            marker.tag = dataList.id
            marker.icon = OverlayImage.fromResource(R.drawable.ic_smoking_marker)

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


