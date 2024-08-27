package com.brandevsolutions.companyassistant.network

import android.os.Message
import com.brandevsolutions.companyassistant.model.SpeechRequest
import com.brandevsolutions.companyassistant.model.SpeechResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class OpenAIRequest(
    val model: String,
    val messages: List<com.brandevsolutions.companyassistant.network.Message>
)

data class OpenAIResponse(
    val choices: List<Choice>
)

data class Choice(
    val text: String  // O asegúrate de que es el campo correcto
)


data class Message(
    val role: String,
    val content: String
)

interface OpenAIApiService {

    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    fun generateText(@Body request: OpenAIRequest): Call<OpenAIResponse>

    @POST("audio/speech")
    fun createSpeech(
        @Body request: SpeechRequest
    ): Call<SpeechResponse>
}