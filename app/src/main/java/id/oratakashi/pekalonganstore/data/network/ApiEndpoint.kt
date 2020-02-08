package id.oratakashi.pekalonganstore.data.network

import id.oratakashi.pekalonganstore.data.model.login.ResponseLogin
import id.oratakashi.pekalonganstore.data.model.register.ResponseRegister
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiEndpoint {

    /**
     * End Point Register
     */
    @FormUrlEncoded
    @POST("users/register")
    fun register(
        @Field("email") email : String,
        @Field("password") password : String,
        @Field("name") name : String
    ) : Single<ResponseRegister>
    /**
     * End Point Login
     */
    @FormUrlEncoded
    @POST("users/login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Single<ResponseLogin>
}