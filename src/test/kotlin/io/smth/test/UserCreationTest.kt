package io.smth.test

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.serpro69.kfaker.Faker
import io.kotest.assertions.asClue
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Link
import io.smth.test.ProjectConfiguration.userClient
import org.example.io.smth.test.models.UserDetails
import org.example.io.smth.test.models.UserErrorResponse
import org.example.io.smth.test.models.UserResponse
import retrofit2.Response
import retrofit2.awaitResponse
@Epic("User creation flow")
@Feature("User create and get endpoints")
@Link("User flow test cases", url = "https://docs.google.com/spreadsheets/d/1vMDXFcb2sEjCJ7lOEgn5nZwVL1VvDUAM30P40NNIsT8/edit?usp=sharing")
class UserCreationTest: FreeSpec() {
    private val objectMapper = ObjectMapper()
    private var faker = Faker()

    init {
        "Scenario: POST /user/create с заполненными полями  username, email, password создает пользователя" - {
            val username = faker.funnyName.name()
            val email = faker.internet.email()
            val password = faker.string.numerify("#######")
            lateinit var userCreateResponse: Response<UserResponse>
            lateinit var userInfoResponse: Response<List<UserDetails>>
            "When client sent request to create the user with email=$email" {
                userCreateResponse = userClient.createUser(username = username, email = email, password = password).awaitResponse()
            }
            "Then client received response with status 200 " {
                userCreateResponse.asClue {
                    it.code() shouldBe 200
                    it.body().shouldNotBeNull().asClue { response ->
                        response.message shouldBe "User Successully created"
                        response.details.shouldNotBeNull().asClue { userDetails ->
                            userDetails.email shouldBe email
                            userDetails.username shouldBe username
                            userDetails.userId.shouldNotBeNull()
                            userDetails.password.shouldNotBeNull()
                            userDetails.createdAt.shouldNotBeNull()
                            userDetails.updatedAt.shouldNotBeNull()
                        }
                    }
                }
            }
            "When client sent request to get the user with email=$email" {
                userInfoResponse =
                    userClient.getUserInfo().awaitResponse()
            }
            "Then client received user info with status 200 " {
                userInfoResponse.asClue {
                    it.code() shouldBe 200
                    it.body().shouldNotBeNull().asClue { response ->
                        response.first{user -> user.email.equals(email)}.shouldNotBeNull().asClue { user ->
                            user.email shouldBe email
                            user.username shouldBe username
                            user.userId.shouldNotBeNull()
                            user.password.shouldNotBeNull()
                            user.createdAt.shouldNotBeNull()
                            user.updatedAt.shouldNotBeNull()
                        }
                    }
                }
            }
        }

        "Scenario: Невозможно создать существующего пользователя с помощью запроса POST user/create" - {
            val username = faker.funnyName.name()
            val email = faker.internet.email()
            val password = faker.string.numerify("#######")
            lateinit var userCreateResponse: Response<UserResponse>
            "When client sent request to create the user with email=$email twice" {
                userClient.createUser(username = username, email = email, password = password).awaitResponse()
                userCreateResponse =
                    userClient.createUser(username = username, email = email, password = password).awaitResponse()
            }
            "Then client received response with status 400 " {
                userCreateResponse.asClue {
                    it.code() shouldBe 400
                    it.errorBody().shouldNotBeNull().asClue { response ->
                        val errorResponse = objectMapper.readValue(response.string(), UserErrorResponse::class.java)
                        errorResponse.message shouldContain "This username is taken. Try another."
                    }
                }
            }
        }

        "Scenario: Невозможно создать пользователя с уже существующим имейлом с помощью запроса POST user/create " - {
            val username = faker.funnyName.name()
            val secondUsername = faker.funnyName.name()
            val email = faker.internet.email()
            val password = faker.string.numerify("#######")
            lateinit var userCreateResponse: Response<UserResponse>
            "When client sent request to create the user with email=$email twice" {
                userClient.createUser(username = username, email = email, password = password).awaitResponse()
                userCreateResponse =
                    userClient.createUser(username = secondUsername, email = email, password = password).awaitResponse()
            }
            "Then client received response with status 400 " {
                userCreateResponse.asClue {
                    it.code() shouldBe 400
                    it.errorBody().shouldNotBeNull().asClue { response ->
                        val errorResponse = objectMapper.readValue(response.string(), UserErrorResponse::class.java)
                        errorResponse.message shouldContain "Email already exists"
                    }
                }
            }
        }
    }
}