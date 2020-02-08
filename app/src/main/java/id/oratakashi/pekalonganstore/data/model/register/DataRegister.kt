package id.oratakashi.pekalonganstore.data.model.register

import com.google.gson.annotations.SerializedName

data class DataRegister (
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("photo") val photo : String?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("subdistrict_id") val subdistrict_id : String?,
    @SerializedName("address") val address : String?,
    @SerializedName("store_id") val store_id : String?
)