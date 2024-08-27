package com.brandevsolutions.companyassistant.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties

object RetrofitClient {

    private lateinit var apiKey: String

    // Inicializa la API Key leyendo de secret.properties
    fun initialize(context: Context) {
        apiKey = loadApiKey(context)
    }

    private fun loadApiKey(context: Context): String {
        val properties = Properties()
        context.assets.open("secret.properties").use {
            properties.load(it)
        }
        return properties.getProperty("OPENAI_API_KEY")
            ?: throw IllegalStateException("API Key not found in secret.properties")
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
        }.build()
    }

    val apiService: OpenAIApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openai.com/v1/") // Agrega el path correcto para las solicitudes
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIApiService::class.java)
    }
}
