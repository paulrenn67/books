package com.fatchickensoftware.books

import android.app.Application
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

class BookApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val koinModules = listOf(bookModule)

        startKoin(this, koinModules)
    }
}

val bookModule = module {
    single { OkHttpClient.Builder().cache(Cache(androidContext().cacheDir, HTTP_CACHE_SIZE)).build() }
    single { BooksAPI(get(), get()) }
}

private const val HTTP_CACHE_SIZE: Long = 1024 * 1024 // 1MB
