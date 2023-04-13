package com.example.wrenchapp.network

import androidx.appcompat.app.AppCompatActivity
import com.example.wrenchapp.BuildConfig
import com.example.wrenchapp.MyApplication
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitInstance {

    companion object {

        fun getAtomRetrofitInstance(): Retrofit? {
            val builder = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
               // .writeTimeout(60,TimeUnit.SECONDS)
            if (BuildConfig.DEBUG)
            { builder.addInterceptor(OkHttpProfilerInterceptor()) }
             val client = builder.build()

            val sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
            val BASE_URL = sf.getString("Atom_Base_Url",null)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }

        fun getNucleusRetrofitInstance() : Retrofit?{

            val builder = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(60, TimeUnit.SECONDS)
                //.writeTimeout(60,TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                builder.addInterceptor(OkHttpProfilerInterceptor()) }
            val client = builder.build()
            val sf = MyApplication.appContext.getSharedPreferences("my_sf_folder", AppCompatActivity.MODE_PRIVATE)
            val BASE_URL = sf.getString("Nucleus_Base_Url",null)
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }
    }
}
