package com.hankim.smokingarea.smokers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hankim.smokingarea.databinding.FragmentSmokersDetailBinding

class SmokersDetailFragment : Fragment() {

    private lateinit var binding: FragmentSmokersDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSmokersDetailBinding.inflate(inflater, container, false)
        return binding.root
    }
}