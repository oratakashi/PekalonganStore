package id.oratakashi.pekalonganstore.data.db

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import id.oratakashi.pekalonganstore.BuildConfig

class Sessions(context: Context) {
    companion object{
        @SuppressLint("StaticFieldLeak")
        var sessions: Sessions? = null
        val PREF_NAME = BuildConfig.APPLICATION_ID+".Sessions"

        fun getInstance(context: Context) : Sessions{
            if(sessions==null){
                sessions = Sessions(context)
            }
            return sessions as Sessions
        }
    }
    var pref : SharedPreferences
    var editor : SharedPreferences.Editor? = null

    var context : Context? = null
    val PRIVATE_MODE : Int = 0

    init {
        this.context = context
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun putString(key : String, value : String){
        editor!!.putString(key, value)
        editor!!.commit()
    }

    fun putInt(key: String, value : Int){
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun putLong(key: String, value: Long){
        editor!!.putLong(key, value)
        editor!!.commit()
    }

    fun putBoolean(key: String, value: Boolean){
        editor!!.putBoolean(key, value)
        editor!!.commit()
    }

    fun getString(key: String) : String{
        return pref.getString(key, "").toString()
    }

    fun getInt(key: String) : Int{
        return pref.getInt(key, 0)
    }

    fun getBoolean(key: String) : Boolean{
        return pref.getBoolean(key, false)
    }

    fun Logout(){
        editor!!.clear()
        editor!!.commit()
    }
}