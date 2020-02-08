package id.oratakashi.pekalonganstore.data.model.register

import com.google.gson.annotations.SerializedName

data class ResponseRegister (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : DataRegister?
)