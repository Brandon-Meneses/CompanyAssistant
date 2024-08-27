package com.brandevsolutions.companyassistant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.brandevsolutions.companyassistant.network.RetrofitClient
import com.brandevsolutions.companyassistant.ui.theme.CompanyAssistantTheme
import com.brandevsolutions.companyassistant.ui.ui.MainScreen
import com.brandevsolutions.companyassistant.ui.ui.OpenAIChatScreen
import com.brandevsolutions.companyassistant.viewmodel.OpenAIViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.initialize(this)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}
