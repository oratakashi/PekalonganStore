package id.oratakashi.pekalonganstore.data.network

import id.oratakashi.pekalonganstore.data.model.login.ResponseLogin
import id.oratakashi.pekalonganstore.data.model.profile.ResponseProfile
import id.oratakashi.pekalonganstore.data.model.region.ResponseSearchSubdistrict
import id.oratakashi.pekalonganstore.data.model.register.ResponseRegister
import id.oratakashi.pekalonganstore.data.model.stores.create.ResponseStoreCreate
import id.oratakashi.pekalonganstore.data.model.users.delete_photo.ResponseDeletePhoto
import id.oratakashi.pekalonganstore.data.model.users.update_photo.ResponseUpdatePhotoProfile
import id.oratakashi.pekalonganstore.data.model.users.update_profile.ResponseUpdateProfile
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

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

    /**
     * End Point Search Subdistrict
     */

    @GET("regions/subdistrict")
    fun getSubdistrict(
        @Query("keyword") keyword : String
    ) : Single<ResponseSearchSubdistrict>

    /**
     * End Point Create Store
     */

    @FormUrlEncoded
    @POST("stores")
    fun postStores(
        @Field("user_id") id : String,
        @Field("name") name : String,
        @Field("description") description : String,
        @Field("subdistrict_id") subdistrict_id : String
    ) : Single<ResponseStoreCreate>

    /**
     * End Point Profile User
     */

    @GET("users/{user_id}")
    fun getProfile(
        @Path("user_id") id : String
    ) : Single<ResponseProfile>

    /**
     * End Point Delete Photo Profile
     */

    @DELETE("users/images/{user_id}")
    fun deletePhotoProfile(
        @Path("user_id") id : String
    ) : Single<ResponseDeletePhoto>

    /**
     * End Point Update Photo Profile
     */

    @Multipart
    @POST("users/images")
    fun postUpdatePhotoProfile(
        @Query("id") id : String,
        @Part images : MultipartBody.Part?
    ) : Single<ResponseUpdatePhotoProfile>

    /**
     * End Point Update Profile
     */

    @FormUrlEncoded
    @PUT("users/{user_id}")
    fun putUpdateProfile(
        @Path("user_id") id : String,
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("phone") phone : String,
        @Field("address") address : String,
        @Field("subdistrict_id") subdistrict_id : String
    ) : Single<ResponseUpdateProfile>
}