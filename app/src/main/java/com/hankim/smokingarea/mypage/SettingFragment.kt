package com.hankim.smokingarea.mypage

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.hankim.smokingarea.ui.LoginActivity
import com.hankim.smokingarea.databinding.FragmentSettingBinding
import com.kakao.sdk.user.UserApiClient

class SettingFragment : Fragment() {

    lateinit var binding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.kakaoLogoutButton.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(context, "로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
        binding.kakaoUnlinkButton.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(context, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        }
    }
}