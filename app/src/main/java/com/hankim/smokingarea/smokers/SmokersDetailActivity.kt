package com.hankim.smokingarea.smokers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hankim.smokingarea.SmokingList
import com.hankim.smokingarea.databinding.ActivitySmokersDetailBinding

class SmokersDetailActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySmokersDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySmokersDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<SmokingList>("data")

        binding.tvSmokersPlaceName.text = data!!.place
        binding.tvSmokersPlaceAddress.text = data!!.address

    }
}