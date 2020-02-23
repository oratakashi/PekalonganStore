package id.oratakashi.pekalonganstore.data.model.users.update_profile

import com.google.gson.annotations.SerializedName

data class DataUpdateProfile (
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("photo") val photo : String?,
    @SerializedName("updated_at") val updated_at : String?,
    @SerializedName("address") val address : String?,
    @SerializedName("subdistrict") val subdistrict : DataSubdistrict?
)