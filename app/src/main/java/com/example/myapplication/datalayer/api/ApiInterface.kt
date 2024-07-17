package com.example.myapplication.datalayer.api

import com.example.myapplication.datalayer.entities.LoginResponse
import com.example.myapplication.datalayer.entities.LogoutDto
import com.example.myapplication.datalayer.entities.SignUpDto
import com.example.myapplication.util.Constants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Retrofit API interface for defining HTTP request methods.
 */
interface ApiInterface {

    @POST(Constants.URL_SIGN_UP)
    suspend fun signUp(@Body map: HashMap<String, Any?>): Response<SignUpDto>

    @POST(Constants.URL_LOGIN)
    suspend fun login(@Body map: HashMap<String, Any?>): Response<LoginResponse>

    @GET(Constants.URL_ACCOUNT_ME)
    suspend fun getUserDetail(): Response<LoginResponse>

    @GET(Constants.URL_LOGOUT)
    suspend fun logout(): Response<LogoutDto>
}

/**
 * Base class representing a failure response.
 * @property data Any additional data associated with the failure.
 * @property statusCode The status code of the failure.
 * @property message The message associated with the failure.
 */
open class Failure(open val data: Any? = null, open val statusCode: Int, open val message: String)

/**
 * Class representing a server error response.
 */
data class ServerError(
    override val data: Any? = null,
    override val statusCode: Int,
    override val message: String
) : Failure(data, statusCode, message)

/**
 * Class representing a timeout error response.
 */
data class TimeoutError(
    override val data: Any? = null,
    override val statusCode: Int,
    override val message: String
) : Failure(data, statusCode, message)

/**
 * Class representing an unknown error response.
 */
data class UnknownError(
    override val data: Any? = null,
    override val statusCode: Int,
    override val message: String
) : Failure(data, statusCode, message)

/**
 * Class representing a no internet connection error response.
 */
data class NoInternetError(
    override val data: Any? = null,
    override val statusCode: Int,
    override val message: String
) : Failure(data, statusCode, message)

/**
 * Class representing an unknown host error response.
 */
data class UnknownHostError(
    override val data: Any? = null,
    override val statusCode: Int,
    override val message: String
) : Failure(data, statusCode, message)