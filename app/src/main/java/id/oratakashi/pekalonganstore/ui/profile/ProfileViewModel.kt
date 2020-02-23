package id.oratakashi.pekalonganstore.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.profile.ResponseProfile
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ProfileViewModel : ViewModel() {
    val progressProfile = MutableLiveData<Boolean>()
    val responseProfile = MutableLiveData<ResponseProfile>()
    val errorProfile = MutableLiveData<Boolean>()

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
}