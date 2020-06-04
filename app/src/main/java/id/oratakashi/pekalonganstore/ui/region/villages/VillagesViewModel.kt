package id.oratakashi.pekalonganstore.ui.region.villages

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import id.oratakashi.pekalonganstore.BuildConfig
import id.oratakashi.pekalonganstore.data.model.region.villages.ResponseSearchVillage
import id.oratakashi.pekalonganstore.root.App
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class VillagesViewModel : ViewModel() {
    val progressSearch = MutableLiveData<Boolean>()
    val responseSearch = MutableLiveData<ResponseSearchVillage>()
    val errorSearch = MutableLiveData<Boolean>()

    val emptySearch = MutableLiveData<Boolean>()

    val showMessage = MutableLiveData<String>()

    fun getSearch(keyword : String){
        progressSearch.value = true
        App.disposable!!.add(
            App.service!!.getVillages(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ResponseSearchVillage>() {
                    override fun onSuccess(t: ResponseSearchVillage) {
                        progressSearch.value = false
                        errorSearch.value = false
                        responseSearch.value = t
                    }

                    override fun onError(e: Throwable) {
                        progressSearch.value = false
                        errorSearch.value = true
                        if(BuildConfig.DEBUG) showMessage.value = e.message
                    }
                })
        )
    }

    fun setupInstantSearch(editText: EditText){
        App.disposable!!.add(
            editText.textChangeEvents()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observeRegion())
        )
    }

    fun observeRegion() : DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onNext(t: TextViewTextChangeEvent) {
                val keyword = t.text.toString()
                if(keyword.trim{it <= ' '}.isNotEmpty() && keyword.trim{it <= ' '}.length >= 3) {
                    getSearch(keyword)
                }else{
                    emptySearch.value = true
                }
            }

            override fun onComplete() {

            }

            override fun onError(e: Throwable) {

            }
        }
    }
}