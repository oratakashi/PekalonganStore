package id.oratakashi.pekalonganstore.data.model.users.update_profile

import com.google.gson.annotations.SerializedName

data class ResponseUpdateProfile (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : DataUpdateProfile?
)