package com.fatchickensoftware.books

import android.view.View
import android.widget.ImageView
import android.widget.TextView

fun TextView.textOrGone(string: String?) {
    text = string
    visibility = if (string?.length?.let { it > 0 } == true) View.VISIBLE else View.GONE
}

fun ImageView.smallThumbnail(volumeInfo: VolumeInfo?) {
    volumeInfo?.imageLinks?.smallThumbnail?.trim()?.takeIf { it.isNotEmpty() }
        .let { loadThumbnail(it) }
}

fun ImageView.thumbnail(volumeInfo: VolumeInfo?) {
    volumeInfo?.imageLinks?.thumbnail?.trim()?.takeIf { it.isNotEmpty() }
        .let { loadThumbnail(it) }
}

private fun ImageView.loadThumbnail(url: String?) {
    url?.let { thumbnailUrl ->
        visibility = View.VISIBLE
        GlideApp.with(context)
            .load(thumbnailUrl)
            .into(this)
    } ?: run { visibility = View.GONE }
}
