package com.brandevsolutions.companyassistant.model

data class SpeechRequest(
    val model: String,
    val voice: String,
    val input: String
)