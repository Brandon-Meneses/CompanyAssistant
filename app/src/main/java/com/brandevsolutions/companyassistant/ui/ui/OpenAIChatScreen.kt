package com.brandevsolutions.companyassistant.ui.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.brandevsolutions.companyassistant.viewmodel.OpenAIViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OpenAIChatScreen(viewModel: OpenAIViewModel = viewModel()) {
    val prompt = remember { mutableStateOf("") }
    val generatedText by viewModel.generatedText.observeAsState("")

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = prompt.value,
            onValueChange = { prompt.value = it },
            label = { Text("Escribe tu mensaje") }
        )
        Button(onClick = { viewModel.generateText(prompt.value) }) {
            Text("Enviar")
        }
        Text(text = generatedText)
    }
}