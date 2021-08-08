package com.example.himanshutestheptagon.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.himanshutestheptagon.R

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, posterPath: String?) {
    posterPath.let {
        Glide.with(imgView.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.load_animation)
                    .error(R.drawable.ic_broken_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .into(imgView)
    }
}