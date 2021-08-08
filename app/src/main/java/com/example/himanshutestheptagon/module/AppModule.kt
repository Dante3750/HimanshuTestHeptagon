package com.example.himanshutestheptagon.module

import com.example.himanshutestheptagon.util.DefaultIfNullFactory
import com.example.himanshutestheptagon.util.EmptyStringAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    val moshi = Moshi.Builder()
        .add(DefaultIfNullFactory())
        .add(EmptyStringAdapter)
        .add(KotlinJsonAdapterFactory())
        .build()
}