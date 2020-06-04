package id.oratakashi.pekalonganstore.ui.addresses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.addresses.ResponseAddresses
import id.oratakashi.pekalonganstore.data.model.addresses.delete.ResponseAddressDelete
import id.oratakashi.pekalonganstore.data.model.addresses.setprimary.ResponseAddressSetPrimary
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class AddressViewModel : ViewModel() {
    val progressAddress = MutableLiveData<Boolean>()
    val responseAddress = MutableLiveData<ResponseAddresses>()
    val errorAddress = MutableLiveData<Boolean>()

    val progressSetPrimary = MutableLiveData<Boolean>()
    val responseSetPrimary = MutableLiveData<ResponseAddressSetPrimary>()
    val errorSetPrimary = MutableLiveData<Boolean>()

    val progressDelete = MutableLiveData<Boolean>()
    val responseDelete = MutableLiveData<ResponseAddressDelete>()
    val errorDelete = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()

    fun getAddress(){
        progressAddress.value = true
        App.disposable!!.add(
            App.service!!.getAddresses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseAddresses>() {
                    override fun onSuccess(t: ResponseAddresses) {
                        progressAddress.value = false
                        errorAddress.value = false
                        responseAddress.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressAddress.value = false
                        errorAddress.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }

    fun putPrimary(id : String){
        progressSetPrimary.value = true
        App.disposable!!.add(
            App.service!!.putStatusAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseAddressSetPrimary>() {
                    override fun onSuccess(t: ResponseAddressSetPrimary) {
                        progressSetPrimary.value = false
                        errorSetPrimary.value = false
                        responseSetPrimary.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressSetPrimary.value = false
                        errorSetPrimary.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }

    fun deleteAddress(id : String){
        progressDelete.value = true
        App.disposable!!.add(
            App.service!!.deleteAddress(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseAddressDelete>() {
                    override fun onSuccess(t: ResponseAddressDelete) {
                        progressDelete.value = false
                        errorDelete.value = false
                        responseDelete.value = t
                    }
                    override fun onError(e: Throwable) {
                        progressDelete.value = false
                        errorDelete.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }
}