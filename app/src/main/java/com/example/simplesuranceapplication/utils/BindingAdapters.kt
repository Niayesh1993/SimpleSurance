package com.example.simplesuranceapplication.utils

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.example.simplesuranceapplication.R

fun bindImage(
    imageView: ImageView,
    imageUrl: String? = null,
    placeholder: Int? = null,
    circleCrop: Boolean? = false,
    crossFade: Boolean? = false,
    overrideWidth: Int? = null,
    overrideHeight: Int? = null,
    listener: RequestListener<Drawable>? = null
) {
    val withContext = Glide.with(imageView.context)
    var completeImageUrl: String? = null
    var imageByte: ByteArray? = null
    if (imageUrl != null) {
        completeImageUrl = imageUrl
    }
    val request = if (!completeImageUrl.isNullOrBlank() || imageByte != null) {
        var builder: RequestBuilder<Drawable>
        builder = withContext
            .load(completeImageUrl)
            .error(R.drawable.bg_no_image)

        placeholder?.also {
            builder = builder.placeholder(it)
        }
        if (circleCrop != null && circleCrop) {
            builder = builder.circleCrop()
        }
        if (crossFade != null && crossFade) {
            builder = builder.transition(DrawableTransitionOptions.withCrossFade())
        }
        if (overrideWidth != null && overrideHeight != null) {
            builder = builder.override(overrideWidth, overrideHeight)
        }
        listener?.also {
            builder = builder.listener(it)
        }
        builder
    } else {
        withContext.load(R.drawable.bg_no_image)
    }
    request.into(imageView)
}