package com.idea.group.phosgithubapp.services

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.idea.group.phosgithubapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Modifier

class BaseNetworkService {
    fun serviceConstructor(ServiceToCon: Class<*>?): Any {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonConverter))
            .build()
        return retrofit.create(ServiceToCon)
    }

    private val gsonConverter: Gson
        get() {
            val b = GsonBuilder()
            return b.excludeFieldsWithModifiers(Modifier.PROTECTED).create()
        }
}
