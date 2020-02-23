package id.oratakashi.pekalonganstore.ui.profile.edit_profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.profile.ResponseProfile
import id.oratakashi.pekalonganstore.data.model.users.delete_photo.ResponseDeletePhoto
import id.oratakashi.pekalonganstore.data.model.users.update_photo.ResponseUpdatePhotoProfile
import id.oratakashi.pekalonganstore.data.model.users.update_profile.ResponseUpdateProfile
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProfileViewModel : ViewModel() {
    val progressProfile = MutableLiveData<Boolean>()
    val responseProfile = MutableLiveData<ResponseProfile>()
    val errorProfile = MutableLiveData<Boolean>()

    val progressDeletePhoto = MutableLiveData<Boolean>()
    val responseDeletePhoto = MutableLiveData<ResponseDeletePhoto>()
    val errorDeletePhoto = MutableLiveData<Boolean>()

    val progressUpdatePhoto = MutableLiveData<Boolean>()
    val responseUpdatePhoto = MutableLiveData<ResponseUpdatePhotoProfile>()
    val errorUpdatePhoto = MutableLiveData<Boolean>()

    val progressUpdateProfile = MutableLiveData<Boolean>()
    val responseUpdateProfile = MutableLiveData<ResponseUpdateProfile>()
    val errorUpdateProfile = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()

    fun getProfile(){
        progressProfile.value = true
        App.disposable!!.add(
            App.service!!.getProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseProfile>() {
                    override fun onSuccess(t: ResponseProfile) {
                        progressProfile.value = false
                        errorProfile.value = false
                        responseProfile.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressProfile.value = false
                        errorProfile.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }

    fun deletePhoto(){
        progressDeletePhoto.value = true
        App.disposable!!.add(
            App.service!!.deletePhotoProfile()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseDeletePhoto>() {
                    override fun onSuccess(t: ResponseDeletePhoto) {
                        progressDeletePhoto.value = false
                        errorDeletePhoto.value = false
                        responseDeletePhoto.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressDeletePhoto.value = false
                        errorDeletePhoto.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }

    fun updatePhoto(images : File?){
        progressUpdatePhoto.value = true

        val request: RequestBody = RequestBody.create(MediaType.parse("image/*"), images!!)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("photo",
            images.name, request)

        App.disposable!!.add(
            App.service!!.postUpdatePhotoProfile(image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseUpdatePhotoProfile>() {
                    override fun onSuccess(t: ResponseUpdatePhotoProfile) {
                        progressUpdatePhoto.value = false
                        errorUpdatePhoto.value = false
                        responseUpdatePhoto.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressUpdatePhoto.value = false
                        errorUpdatePhoto.value = true
                        Log.e("E.Error", e.message)
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }

    fun putProfile(
        name : String,
        email : String,
        phone : String,
        address : String,
        subdistrict_id : String
    ){
        progressUpdateProfile.value = true
        App.disposable!!.add(
            App.service!!.putUpdateProfile(
                name, email, phone, address, subdistrict_id
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseUpdateProfile>() {
                    override fun onSuccess(t: ResponseUpdateProfile) {
                        progressUpdateProfile.value = false
                        errorUpdateProfile.value = false
                        responseUpdateProfile.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressUpdateProfile.value = false
                        errorUpdateProfile.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }
}