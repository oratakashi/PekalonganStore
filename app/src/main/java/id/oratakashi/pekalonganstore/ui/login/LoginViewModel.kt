package id.oratakashi.pekalonganstore.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.login.ResponseLogin
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {
    val progressLogin = MutableLiveData<Boolean>()
    val responseLogin = MutableLiveData<ResponseLogin>()
    val errorLogin = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()

    fun postLogin(email: String, password: String) {
        progressLogin.value = true
        App.disposable!!.add(
            App.service!!.postLogin(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseLogin>() {
                    override fun onSuccess(t: ResponseLogin) {
                        progressLogin.value = false
                        errorLogin.value = true
                        responseLogin.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressLogin.value = false
                        errorLogin.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }
}