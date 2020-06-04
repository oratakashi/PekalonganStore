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

        /**
         * User Sessions
         */

        val id_user : String = "id_user"
        val name : String = "name"
        val email : String = "email"
        val phone : String = "phone"
        val photo : String = "photo"
        val address : String = "address"
        val store_id : String = "store_id"
        val regency_id : String = "regency_id"
        val regency : String = "regency"
        val village_id : String = "village_id"
        val village : String = "village"
        val province_id : String = "province_id"
        val province : String = "province"
        val district_id : String = "district_id"
        val district : String = "district"
        val type : String = "type"

        /**
         * Store Sessions
         */

        val store_subdistrict_id : String = "store_subdistrict_id"
        val store_province : String = "store_province"
        val store_regency : String = "store_regency"
        val store_district : String = "store_district"
        val store_name : String = "store_name"
        val store_description : String = "store_description"
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

    fun isLogin() : Boolean{
        return getString(id_user).isNotEmpty()
    }

    fun Logout(){
        editor!!.clear()
        editor!!.commit()
    }
}