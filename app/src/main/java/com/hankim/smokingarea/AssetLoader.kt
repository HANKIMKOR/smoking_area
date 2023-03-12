package com.hankim.smokingarea

import android.content.Context

class AssetLoader {

    fun getJsonString(context: Context, fileName: String): String? {
        return kotlin.runCatching {
            loadAsset(context, fileName)
        }.getOrNull()
    }
    private fun loadAsset(context: Context, fileName: String): String {
        //json 사용부분
        return context.assets.open(fileName).use { inputStream ->
            val size = inputStream.available()
            val bytes = ByteArray(size)
            inputStream.read(bytes)
            String(bytes)
        }
    }
}