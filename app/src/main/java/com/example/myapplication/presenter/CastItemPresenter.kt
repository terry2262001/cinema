package com.example.myapplication.presenter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.models.CastResponse
import com.example.myapplication.utils.Common

class CastItemPresenter : Presenter() {
    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder? {
        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.cast_item_view, parent, false)
        val params = view.layoutParams
//        params.width = Common.getWidthPercent(parent!!.context, 15)
//        params.height = Common.getHeightPercent(parent!!.context, 21)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder?,
        item: Any?
    ) {
        val content = item as? CastResponse.Cast
        val image = viewHolder?.view?.findViewById<ImageView>(R.id.cast_img)
        val path = "https://www.themoviedb.org/t/p/w780" + content?.profile_path
        Glide.with(viewHolder?.view?.context!!)
            .load(path)
            .apply(RequestOptions.circleCropTransform())
            .into(image!!)

    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
    }
}