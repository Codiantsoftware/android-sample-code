package com.example.myapplication.di

import com.example.myapplication.datalayer.api.ApiInterface
import com.example.myapplication.datalayer.entities.UserDto
import com.example.myapplication.local.AppPreference
import com.example.myapplication.local.PreferenceKeys
import com.example.myapplication.util.Constants
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Dagger Hilt module for providing network-related dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides the API interface for making network requests.
     * @param retrofit The Retrofit instance.
     * @return An instance of ApiInterface.
     */
    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    /**
     * Provides the Retrofit instance.
     * @param gson The Gson instance.
     * @param okHttpClient The OkHttpClient instance.
     * @return The Retrofit instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
        }.build()


    /**
     * Provides the Gson instance.
     * @return The Gson instance.
     */
    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .setLenient()
            .create()


    /**
     * Provides the OkHttpClient instance.
     * @return The OkHttpClient instance.
     */
    @Singleton
    @Provides
    fun getHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor { chain ->
            var request = chain.request()
            val requestBuilder = request.newBuilder()

            requestBuilder.addHeader("Accept", "application/json")
            requestBuilder.addHeader("Content-Type", "application/json")

            if (AppPreference.get<UserDto>(PreferenceKeys.USER_DATA)?.token != null) {
                requestBuilder.addHeader(
                    "authorization", "Bearer " +
                            AppPreference.get<UserDto>(PreferenceKeys.USER_DATA)?.token
                )
            }
            request = requestBuilder.build()
            chain.proceed(request)
        }
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.writeTimeout(60, TimeUnit.SECONDS)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClientBuilder.addInterceptor(loggingInterceptor)
        return httpClientBuilder.build()
    }


}