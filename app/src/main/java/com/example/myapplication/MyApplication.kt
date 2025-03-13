package com.example.myapplication

import android.app.Application
import com.example.myapplication.api.ApiService
import com.example.myapplication.api.RetrofitHelper
import com.example.myapplication.api.TmdbRepo

class MyApplication: Application() {

    lateinit var tmdbRepo: TmdbRepo

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        val service = RetrofitHelper.getInstance().create(ApiService::class.java)
        tmdbRepo = TmdbRepo(service)
    }
}