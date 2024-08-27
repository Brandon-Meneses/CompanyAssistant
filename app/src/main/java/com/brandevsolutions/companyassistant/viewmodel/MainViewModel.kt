package com.brandevsolutions.companyassistant.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandevsolutions.companyassistant.network.OllamaClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject

import android.util.Log

class MainViewModel : ViewModel() {

    // Usamos MutableState para que la UI observe los cambios
    var chatResponse = mutableStateOf("Esperando respuesta...")
        private set

    fun generateResponse(prompt: String) {
        Log.d("MainViewModel", "generateResponse: Prompt enviado: $prompt")
        viewModelScope.launch(Dispatchers.IO) {
            var finalResponse = ""

            try {
                OllamaClient.generateResponse(prompt) { response ->
                    if (response != null) {
                        Log.d("MainViewModel", "generateResponse: Respuesta recibida: $response")
                        try {
                            // Aquí dividimos las respuestas parciales si vienen en el mismo response
                            val responses = response.split("\n")

                            responses.forEach { singleResponse ->
                                // Verificar si la respuesta es vacía o no válida
                                if (singleResponse.isNotEmpty() && singleResponse.isNotBlank()) {
                                    try {
                                        val jsonResponse = JSONObject(singleResponse)
                                        val partialResponse = jsonResponse.getString("response")
                                        finalResponse += partialResponse

                                        Log.d("MainViewModel", "generateResponse: Respuesta parcial: $partialResponse")

                                        // Actualizamos el estado en el hilo principal
                                        viewModelScope.launch(Dispatchers.Main) {
                                            chatResponse.value = finalResponse
                                            Log.d("MainViewModel", "generateResponse: Estado actualizado con: $finalResponse")
                                        }

                                        // Verificamos si ya terminó
                                        val done = jsonResponse.getBoolean("done")
                                        if (done) {
                                            Log.d("MainViewModel", "generateResponse: Respuesta final completada")
                                        }
                                    } catch (e: JSONException) {
                                        Log.e("MainViewModel", "generateResponse: Error de formato JSON en fragmento: $singleResponse", e)
                                    }
                                }
                            }
                        } catch (e: JSONException) {
                            Log.e("MainViewModel", "generateResponse: Error de formato JSON", e)
                            viewModelScope.launch(Dispatchers.Main) {
                                chatResponse.value = "Error en el formato de la respuesta"
                            }
                        }
                    } else {
                        Log.e("MainViewModel", "generateResponse: Respuesta nula")
                        viewModelScope.launch(Dispatchers.Main) {
                            chatResponse.value = "Error: respuesta nula"
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "generateResponse: Error al realizar la solicitud", e)
                viewModelScope.launch(Dispatchers.Main) {
                    chatResponse.value = "Error al realizar la solicitud"
                }
            }
        }
    }
}

