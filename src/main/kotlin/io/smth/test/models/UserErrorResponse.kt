package org.example.io.smth.test.models

import com.fasterxml.jackson.annotation.JsonProperty

data class UserErrorResponse(
    @JsonProperty("success")
    val success: Boolean,
    @JsonProperty("message")
    val message: List<String>,
)
