package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityDetailBinding
import com.example.myapplication.models.DetailResponse
import com.example.myapplication.utils.Common
import com.example.myapplication.utils.Common.Companion.isEllipsized
import com.example.myapplication.viewmodel.DetailViewModel
import com.example.myapplication.viewmodel.DetailViewModelFactory


class DetailActivity : FragmentActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewmodel : DetailViewModel
    val castFragment = ListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        addFragment(castFragment)

        val movieId = intent.getIntExtra("id", 0)
        val repository = (application as MyApplication).tmdbRepo
        viewmodel = ViewModelProvider(this, DetailViewModelFactory(repository,movieId))
            .get(DetailViewModel::class.java)

        viewmodel.movieDetails.observe(this) {
            when (it) {
                is com.example.myapplication.api.Response.Loading -> {

                }
                is com.example.myapplication.api.Response.Success -> {
                    setData(it.data)
                }
                is com.example.myapplication.api.Response.Error -> {

                }
            }
        }

        viewmodel.castDetails.observe(this) {
            when (it) {
                is com.example.myapplication.api.Response.Loading -> {

                }
                is com.example.myapplication.api.Response.Success -> {
                    if (!it.data?.cast.isNullOrEmpty()) {
                        it.data?.cast?.let { casts -> castFragment.bindCastData(casts) }
                    }
                }
                is com.example.myapplication.api.Response.Error -> {

                }
            }
        }
    }

    private fun addFragment(fragment: ListFragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.cast_fragment, fragment)
        transaction.commit()
    }

    private fun setData(response: DetailResponse?) {
        binding.tvTitle.text = response?.title ?: ""
        binding.tvSubtitle.text = getSubTitle(response)
        binding.description.text = response?.overview ?: ""
        val path = "https://image.tmdb.org/t/p/w780" + response?.backdrop_path
        Glide.with(this).load(path).into(binding.imgBanner)
        binding.description.post {
            binding.description.isEllipsized { ellipsized ->
                binding.showMore.visibility = if (ellipsized) View.VISIBLE else View.GONE
                binding.showMore.setOnClickListener {
                    Common.descriptionDialog(
                        this,
                        response?.title ,
                        getSubTitle(response),
                        response?.overview.toString()
                    )
                }

            }
        }
    }

    fun getSubTitle(response: DetailResponse?): String {
        val rating = response?.let {
            if (it.adult) {
                "18+"
            } else {
                "13+"
            }
        }
        val genres = response?.genres?.joinToString(
            prefix = " ",
            postfix = ".",
            separator = "."
        ){it.name}
        val hours : Int = response?.runtime!! / 60
        val min : Int = response.runtime % 60
        return rating + " | " + hours + "h" + min + "m" + " | " + genres
    }
}