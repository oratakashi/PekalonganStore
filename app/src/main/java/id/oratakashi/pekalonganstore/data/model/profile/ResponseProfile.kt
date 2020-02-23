package id.oratakashi.pekalonganstore.data.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseProfile (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : DataProfile?
)