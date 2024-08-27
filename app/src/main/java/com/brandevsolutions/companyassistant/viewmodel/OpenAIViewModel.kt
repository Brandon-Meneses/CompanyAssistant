package com.brandevsolutions.companyassistant.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandevsolutions.companyassistant.network.Message
import com.brandevsolutions.companyassistant.network.OpenAIApiService
import com.brandevsolutions.companyassistant.network.OpenAIRequest
import com.brandevsolutions.companyassistant.network.OpenAIResponse
import com.brandevsolutions.companyassistant.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OpenAIViewModel : ViewModel() {

    private val _generatedText = MutableLiveData<String>()
    val generatedText: LiveData<String> get() = _generatedText

    fun generateText(prompt: String) {
        val request = OpenAIRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(
                Message(role = "system", content = "You are a helpful assistant."),
                Message(role = "user", content = prompt)
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            val call = RetrofitClient.apiService.generateText(request)
            call.enqueue(object : Callback<OpenAIResponse> {
                override fun onResponse(
                    call: Call<OpenAIResponse>,
                    response: Response<OpenAIResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseContent = response.body()?.choices?.get(0)?.text
                        _generatedText.postValue(responseContent ?: "No response content")
                    } else {
                        _generatedText.postValue("Error: ${response.code()}")
                    }
                }




                override fun onFailure(call: Call<OpenAIResponse>, t: Throwable) {
                    _generatedText.postValue("Error: ${t.message}")
                }
            })
        }
    }
}
