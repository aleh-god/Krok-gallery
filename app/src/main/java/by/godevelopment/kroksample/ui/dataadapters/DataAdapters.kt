package by.godevelopment.kroksample.ui.dataadapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import by.godevelopment.kroksample.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("isVisible")
fun setIsVisible(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context)
            .load(url)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .error(R.drawable.image_not_loaded)
            .placeholder(R.drawable.image)
            .into(view)
    }
}