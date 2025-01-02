package io.smth.test

import org.example.io.smth.test.clients.UserClient
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.allure.AllureTestReporter
import retrofit2.Retrofit
import retrofit2.create
import org.example.io.smth.test.utils.HttpUtil
import org.example.io.smth.test.utils.HttpUtil.allureInterceptor
import org.example.io.smth.test.utils.HttpUtil.jsonConverter
import org.example.io.smth.test.utils.HttpUtil.slf4jInterceptor

object ProjectConfiguration :  AbstractProjectConfig(){

    val retrofitJson: Retrofit by lazy {
        HttpUtil.createRetrofit(
            baseUrl = "http://3.73.86.8:3333",
            converter = jsonConverter,
            interceptors = listOf(allureInterceptor, slf4jInterceptor)
        )
    }

    val userClient: UserClient by lazy { retrofitJson.create() }

    override fun extensions(): List<Extension> = listOf(AllureTestReporter())
}