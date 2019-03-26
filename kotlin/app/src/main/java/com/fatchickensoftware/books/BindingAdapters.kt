package com.fatchickensoftware.books

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView

@BindingAdapter("textOrGone")
fun textOrGone(textView: TextView, string: String?) {
    textView.textOrGone(string)
}

@BindingAdapter("getPublisherLine")
fun getPublisherLine(textView: TextView, volumeInfo: VolumeInfo?) {
    textView.textOrGone((volumeInfo?.publishedDate ?.let { "$it " } ?: "") + (volumeInfo?.publisher ?.let { it } ?: ""))
}

@BindingAdapter("formatDescription")
fun formatDescription(textView: TextView, volumeInfo: VolumeInfo?) {
    volumeInfo?.description ?: textView.resources.getString(R.string.no_description).let { description ->
        textView.text = description.replace(". ", ".\n\n")
    }
}

@BindingAdapter("smallThumbnail")
fun smallThumbnail(imageView: ImageView, volumeInfo: VolumeInfo?) {
    imageView.smallThumbnail(volumeInfo)
}

@BindingAdapter("thumbnail")
fun thumbnail(imageView: ImageView, volumeInfo: VolumeInfo?) {
    imageView.thumbnail(volumeInfo)
}
