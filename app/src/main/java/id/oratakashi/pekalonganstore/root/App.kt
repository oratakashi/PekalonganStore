package id.oratakashi.pekalonganstore.root

import android.app.Application
import id.oratakashi.pekalonganstore.data.db.Sessions
import id.oratakashi.pekalonganstore.data.network.ApiService
import io.reactivex.disposables.CompositeDisposable

class App : Application() {

    /**
     * Class App Digunakan untuk Base Class
     * Jadi Fungsi2 yang pasti di pakai selama aplikasi
     * akan di deklarasikan di sini, dan di class yang lainya hanya tinggal memanggil saja
     *
     * Contoh :
     * Butuh SharedPreference yang ada di class Sessions, Kita ga perlu mendeklarasikan di setiap class
     * yang membutuhkan, kita tinggal panggil App.sessions.getString(Session.user_id)
     */

    companion object{
        var service : ApiService ?= null
        var disposable : CompositeDisposable?= null
        var sessions : Sessions?= null
    }

    override fun onCreate() {
        super.onCreate()

        service = ApiService()
        disposable = CompositeDisposable()
        sessions = Sessions(this)
    }

}