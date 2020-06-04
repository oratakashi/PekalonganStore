package id.oratakashi.pekalonganstore.data.network

import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.data.model.addresses.ResponseAddresses
import id.oratakashi.pekalonganstore.data.model.addresses.delete.ResponseAddressDelete
import id.oratakashi.pekalonganstore.data.model.addresses.setprimary.ResponseAddressSetPrimary
import id.oratakashi.pekalonganstore.data.model.login.ResponseLogin
import id.oratakashi.pekalonganstore.data.model.profile.ResponseProfile
import id.oratakashi.pekalonganstore.data.model.region.subdistrict.ResponseSearchSubdistrict
import id.oratakashi.pekalonganstore.data.model.region.villages.ResponseSearchVillage
import id.oratakashi.pekalonganstore.data.model.register.ResponseRegister
import id.oratakashi.pekalonganstore.data.model.stores.create.ResponseStoreCreate
import id.oratakashi.pekalonganstore.data.model.users.delete_photo.ResponseDeletePhoto
import id.oratakashi.pekalonganstore.data.model.users.update_photo.ResponseUpdatePhotoProfile
import id.oratakashi.pekalonganstore.data.model.users.update_profile.ResponseUpdateProfile
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    private val api : ApiEndpoint

    init{
        val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else
                    HttpLoggingInterceptor.Level.NONE
            })
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
        val base_url = "https://api.oratakashi.com/pstore/"
//        val base_url = "http://192.168.42.27/store_api/"
        api = Retrofit.Builder()
            .baseUrl(base_url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiEndpoint::class.java)
    }

    fun postRegister(
        email: String,
        password: String,
        name: String
    ) : Single<ResponseRegister> {
        return api.register(email, password, name)
    }

    fun postLogin(email: String, password: String): Single<ResponseLogin> {
        return api.login(email, password)
    }

    fun getSubdistrict(keyword : String) : Single<ResponseSearchSubdistrict>{
        return api.getSubdistrict(keyword)
    }

    fun postStores(
        name : String,
        description : String,
        subdistrict_id : String
    ) : Single<ResponseStoreCreate>{
        return api.postStores(
            App.sessions!!.getString(Sessions.id_user),
            name, description, subdistrict_id
        )
    }

    fun getProfile() : Single<ResponseProfile>{
        return api.getProfile(
            App.sessions!!.getString(Sessions.id_user)
        )
    }

    fun deletePhotoProfile() : Single<ResponseDeletePhoto>{
        return api.deletePhotoProfile(
            App.sessions!!.getString(Sessions.id_user)
        )
    }

    fun postUpdatePhotoProfile(
        images : MultipartBody.Part
    ) : Single<ResponseUpdatePhotoProfile>{
        return api.postUpdatePhotoProfile(
            App.sessions!!.getString(Sessions.id_user),
            images
        )
    }

    fun putUpdateProfile(
        name : String,
        email : String,
        phone : String,
        address : String,
        village_id : String
    ) : Single<ResponseUpdateProfile>{
        return api.putUpdateProfile(
            App.sessions!!.getString(Sessions.id_user),
            name, email, phone, address, village_id
        )
    }

    fun getAddresses() : Single<ResponseAddresses>{
        return api.getAddresses(
            App.sessions!!.getString(Sessions.id_user)
        )
    }

    fun getVillages(
        keyword : String
    ) : Single<ResponseSearchVillage>{
        return api.getVillages(keyword)
    }

    fun putStatusAddress(
        id : String
    ) : Single<ResponseAddressSetPrimary>{
        return api.putPrimaryAddress(
            id,
            App.sessions!!.getString(Sessions.id_user)
        )
    }

    fun deleteAddress(
        id : String
    ) : Single<ResponseAddressDelete>{
        return api.deleteAddress(id)
    }
}