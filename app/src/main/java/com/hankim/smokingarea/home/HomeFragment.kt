package com.hankim.smokingarea.home

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hankim.smokingarea.R
import com.hankim.smokingarea.SearchData
import com.hankim.smokingarea.SmokingList
import com.hankim.smokingarea.databinding.FragmentHomeBinding
import com.hankim.smokingarea.network.ApiClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.LocationButtonView
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private lateinit var currentLocationButton: LocationButtonView

    // Viewpager2
    private lateinit var viewPager: ViewPager2
    private var viewPagerAdapter = HomeBannerAdapter()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //Firebase
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = binding

        // 맵뷰 구성
        mapView = rootView.mapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // 뷰페이저 구성
        viewPager = rootView.homeViewPager
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

        return rootView.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        _binding = fragmentHomeBinding

        currentLocationButton = fragmentHomeBinding.btCurrentLocation
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        fragmentHomeBinding.addFloatingButton.setOnClickListener {
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

    override fun registerForContextMenu(view: View) {
        if (!locationSource.isActivated) { // 권한 거부됨
            naverMap.locationTrackingMode = LocationTrackingMode.None
        }
        return

        super.registerForContextMenu(view)
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


