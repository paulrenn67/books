package com.fatchickensoftware.books;


import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BindingAdapters {
    @BindingAdapter("textOrGone")
    public static void textOrGone(TextView textView, String string) {
        textView.setText(string);
        textView.setVisibility(string != null && string.length() > 0 ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("getPublisherLine")
    public static void getPublisherLine(TextView textView, VolumeInfo volumeInfo) {
        if (volumeInfo.publishedDate != null && volumeInfo.publisher != null) {
            textOrGone(textView, volumeInfo.publishedDate + " " + volumeInfo.publisher);
        } else if (volumeInfo.publishedDate != null) {
            textOrGone(textView, volumeInfo.publishedDate);
        } else if (volumeInfo.publisher != null) {
            textOrGone(textView, volumeInfo.publisher);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    @BindingAdapter("formatDescription")
    public static void formatDescription(TextView textView, VolumeInfo volumeInfo) {
        String description = volumeInfo.description != null ? volumeInfo.description : textView.getResources().getString(R.string.no_description);
        textView.setText(description.replace(". ", ".\n\n"));
    }

    @BindingAdapter("smallThumbnail")
    public static void smallThumbnail(ImageView imageView, VolumeInfo volumeInfo) {
        if (volumeInfo.imageLinks != null) {
            if (volumeInfo.imageLinks.smallThumbnail != null) {
                loadThumbnail(imageView, volumeInfo.imageLinks.smallThumbnail);
            } else {
                loadThumbnail(imageView, null);
            }
        }
    }

    @BindingAdapter("thumbnail")
    public static void thumbnail(ImageView imageView, VolumeInfo volumeInfo) {
        if (volumeInfo.imageLinks != null) {
            if (volumeInfo.imageLinks.thumbnail != null) {
                loadThumbnail(imageView, volumeInfo.imageLinks.smallThumbnail);
            } else {
                loadThumbnail(imageView, null);
            }
        }
    }

    private static void loadThumbnail(ImageView imageView, String url) {
        if (url != null) {
            imageView.setVisibility(View.VISIBLE);
            GlideApp.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        } else {
            imageView.post(() -> imageView.setVisibility(View.GONE));
        }
    }
}
