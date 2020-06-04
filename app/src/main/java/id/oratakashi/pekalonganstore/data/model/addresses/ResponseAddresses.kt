package id.oratakashi.pekalonganstore.data.model.addresses

import com.google.gson.annotations.SerializedName

data class ResponseAddresses (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : List<DataAddresses>?
)