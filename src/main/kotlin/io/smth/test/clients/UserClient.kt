package org.example.io.smth.test.clients

import org.example.io.smth.test.models.UserDetails
import org.example.io.smth.test.models.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserClient {
    @GET("/user/get")
    fun getUserInfo(): Call<List<UserDetails>>

    @FormUrlEncoded
    @POST("/user/create")
    fun createUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>
}