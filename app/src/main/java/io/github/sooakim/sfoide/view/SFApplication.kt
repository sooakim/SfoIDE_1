package io.github.sooakim.sfoide.view

import android.app.Application
import io.github.sooakim.sfoide.remote.di.remoteModule
import io.github.sooakim.sfoide.view.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SFApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SFApplication)
            modules(
                    remoteModule,
                    viewModelModule
            )
        }
    }
}