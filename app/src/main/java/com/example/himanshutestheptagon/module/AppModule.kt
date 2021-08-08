package com.example.himanshutestheptagon.module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.bumptech.glide.Registry
import com.bumptech.glide.RequestManager
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.with
import com.bumptech.glide.module.AppGlideModule
import com.example.himanshutestheptagon.BuildConfig
import com.example.himanshutestheptagon.data.api.ApiHelper
import com.example.himanshutestheptagon.data.api.ApiHelperImpl
import com.example.himanshutestheptagon.data.api.ApiService
import com.example.himanshutestheptagon.util.AppConstant
import com.example.himanshutestheptagon.util.DefaultIfNullFactory
import com.example.himanshutestheptagon.util.EmptyStringAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStream
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    val moshi = Moshi.Builder()
        .add(DefaultIfNullFactory())
        .add(EmptyStringAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideBaseUrl() = AppConstant.URL

    @Provides
    @Singleton
    fun providesOkHttpClient(@ApplicationContext context: Context): OkHttpClient {

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        val networkCacheInterceptor = createCacheInterceptor()
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(networkCacheInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(interceptor)



        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(AppConstant.URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    private fun createCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            var cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.MINUTES)
                .build()
            response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    @Module
    @InstallIn(FragmentComponent::class)
    class HiltModule {
        @Provides
        fun provideGlide(@ActivityContext context: Context): RequestManager = Glide.with(context)

    }
}