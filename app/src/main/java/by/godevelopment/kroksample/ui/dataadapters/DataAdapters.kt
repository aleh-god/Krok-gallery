package by.godevelopment.kroksample.ui.dataadapters

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import by.godevelopment.kroksample.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.util.concurrent.TimeUnit

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

@BindingAdapter("htmlText")
fun setHtmlText(view: TextView, html: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.text = Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
            view.text = Html.fromHtml(html)
        }
}

@BindingAdapter("valueToTimeText")
fun setValueToTimeText(view: TextView, millis: Int) {
    view.text = String.format(
        "%02d:%02d",
        TimeUnit.MILLISECONDS.toMinutes(millis.toLong()),
        TimeUnit.MILLISECONDS.toSeconds(millis.toLong()) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis.toLong()))
    )
}

@BindingAdapter("setLiveButton")
fun setLiveButton(view: ImageButton, playStatus: Boolean) {
    if (playStatus) view.setImageResource(R.drawable.ic_pause_circle)
    else view.setImageResource(R.drawable.ic_play_circle)
}