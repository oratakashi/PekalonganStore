package id.oratakashi.pekalonganstore.ui.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.register.ResponseRegister
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RegisterViewModel : ViewModel() {
    val progressRegister = MutableLiveData<Boolean>()
    val responseRegister = MutableLiveData<ResponseRegister>()
    val errorRegister = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()

    fun postRegister(
        email : String,
        password : String,
        name : String
    ){
        progressRegister.value = true
        App.disposable!!.add(
            App.service!!.postRegister(email, password, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseRegister>() {
                    override fun onSuccess(t: ResponseRegister) {
                        progressRegister.value = false
                        errorRegister.value = false
                        responseRegister.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressRegister.value = false
                        errorRegister.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }
}