package com.brandevsolutions.companyassistant.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType

object OllamaClient {
    private val client = OkHttpClient()

    fun generateResponse(prompt: String, callback: (String?) -> Unit) {
        val url = "http://10.0.2.2:11434/api/generate"  // Usamos la IP para el emulador
        val json = """{"model": "qwen2:1.5b", "prompt": "$prompt"}"""  // Cambiado a "qwen2:1.5b"
        val body = json.toRequestBody("application/json".toMediaType())

        Log.d("OllamaClient", "generateResponse: Realizando solicitud a Ollama con prompt: $prompt")

        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("OllamaClient", "generateResponse: Falló la solicitud, código de error: ${response.code}")
                callback(null)
            } else {
                val responseBody = response.body?.string()
                Log.d("OllamaClient", "generateResponse: Respuesta obtenida: $responseBody")
                callback(responseBody)
            }
        }
    }
}

