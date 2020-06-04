package id.oratakashi.pekalonganstore.data.model.addresses.delete

import com.google.gson.annotations.SerializedName

data class ResponseAddressDelete (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?
)