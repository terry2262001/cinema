package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.Response
import com.example.myapplication.api.TmdbRepo
import com.example.myapplication.models.CastResponse
import com.example.myapplication.models.DetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(val repo: TmdbRepo, id: Int) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getMovieDetails(id)
            repo.getMovieCast(id)
        }
    }

    val movieDetails: LiveData<Response<DetailResponse>>
        get() = repo.movieDetail

    val castDetails: LiveData<Response<CastResponse>>
        get() = repo.castDetail



}