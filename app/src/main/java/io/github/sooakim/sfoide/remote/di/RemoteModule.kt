package io.github.sooakim.sfoide.remote.di

import io.github.sooakim.sfoide.BuildConfig
import io.github.sooakim.sfoide.remote.api.UserApi
import io.github.sooakim.sfoide.remote.converter.EnumConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val remoteModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            }else{
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single { RxJava2CallAdapterFactory.create() }

    single { GsonConverterFactory.create() }

    single { EnumConverterFactory.create() }

    single{
        OkHttpClient.Builder()
                .addInterceptor(get<HttpLoggingInterceptor>())
                .build()
    }

    single {
        Retrofit.Builder()
                .addCallAdapterFactory(get<RxJava2CallAdapterFactory>())
                .addConverterFactory(get<GsonConverterFactory>())
                .addConverterFactory(get<EnumConverterFactory>())
                .baseUrl(BuildConfig.BASE_URL)
                .client(get<OkHttpClient>())
                .build()
    }

    single{ get<Retrofit>().create(UserApi::class.java) }
}