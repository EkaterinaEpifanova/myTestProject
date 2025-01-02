package org.example.io.smth.test.models

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    @JsonProperty("success")
    val success: Boolean,
    @JsonProperty("details")
    val details: UserDetails?,
    @JsonProperty("message")
    val message: String,
)
data class UserDetails(
    @JsonProperty("username")
    val username: String,
    @JsonProperty("email")
    val email: String?,
    @JsonProperty("password")
    val password: String,
    @JsonProperty("created_at")
    val createdAt: String,
    @JsonProperty("updated_at")
    val updatedAt: String,
    @JsonProperty("id")
    val userId: Int,
)
