package com.newsapp

import android.app.Application
import com.facebook.stetho.Stetho
import com.newsapp.data.local.NewsDB
import com.newsapp.data.remote.NewsApi
import com.newsapp.data.remote.NewsRepository
import com.newsapp.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class NewsApp : Application() {

    private var koinModuleList = module {
        single { NewsApi() }
        single { NewsDB(this@NewsApp) }
        single { NewsRepository(get(), get()) }
        viewModel { MainViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initKoin()
        initStetho()

    }


    private fun initStetho() {
        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@NewsApp)
            modules(koinModuleList)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
