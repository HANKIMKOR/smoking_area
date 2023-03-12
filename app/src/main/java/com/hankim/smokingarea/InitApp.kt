package com.hankim.smokingarea

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class InitApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }
}


// for kakaomap