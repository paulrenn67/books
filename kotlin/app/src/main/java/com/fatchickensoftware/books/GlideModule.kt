package com.fatchickensoftware.books

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import okhttp3.OkHttpClient
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.InputStream


@GlideModule
class GlideAppModule() : KoinComponent, AppGlideModule() {

    private val client: OkHttpClient by inject()

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        glide.registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(client))
    }
}