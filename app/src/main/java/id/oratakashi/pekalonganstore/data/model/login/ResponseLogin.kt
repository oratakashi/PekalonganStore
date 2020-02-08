package id.oratakashi.pekalonganstore.data.model.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : DataLogin?
)