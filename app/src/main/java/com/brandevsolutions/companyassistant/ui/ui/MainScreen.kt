package com.brandevsolutions.companyassistant.ui.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brandevsolutions.companyassistant.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    var userInput by remember { mutableStateOf("") }
    val chatResponse by viewModel.chatResponse  // Observamos el estado del ViewModel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Escribe tu mensaje") }
        )

        Button(onClick = {
            Log.d("MainScreen", "Botón Enviar presionado. Enviando prompt: $userInput")
            viewModel.generateResponse(userInput)
        }) {
            Text("Enviar")
        }

        Text(
            text = "Respuesta: $chatResponse",  // La UI se actualiza automáticamente
            modifier = Modifier.padding(top = 20.dp)
        )

        Log.d("MainScreen", "Estado actual de chatResponse: $chatResponse")
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

