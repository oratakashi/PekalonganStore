package id.oratakashi.pekalonganstore.ui.storeuser.create

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.stores.create.ResponseStoreCreate
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver

import io.reactivex.schedulers.Schedulers

class StoreUserViewModel : ViewModel() {
    val progressStore = MutableLiveData<Boolean>()
    val responseStore = MutableLiveData<ResponseStoreCreate>()
    val errorStore = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()

    fun postStoreCreate(
        name : String,
        description : String,
        subdistrict_id : String
    ){
        progressStore.value = true
        App.disposable!!.add(
            App.service!!.postStores(
                name, description, subdistrict_id
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseStoreCreate>() {
                    override fun onSuccess(t: ResponseStoreCreate) {
                        progressStore.value = false
                        errorStore.value = false
                        responseStore.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressStore.value = false
                        errorStore.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }
}