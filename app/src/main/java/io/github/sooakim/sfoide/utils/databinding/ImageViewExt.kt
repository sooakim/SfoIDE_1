package io.github.sooakim.sfoide.utils.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("circleImage")
fun ImageView.bindCircleImage(imagePath: String?) {
    Glide.with(this)
            .load(imagePath)
            .apply(RequestOptions.bitmapTransform(
                    MultiTransformation(
                            CenterCrop(),
                            CircleCrop()
                    )
            ))
            .into(this)
}

@BindingAdapter("squareImage")
fun ImageView.bindSquareImage(imagePath: String?) {
    Glide.with(this)
            .load(imagePath)
            .into(this)
}
