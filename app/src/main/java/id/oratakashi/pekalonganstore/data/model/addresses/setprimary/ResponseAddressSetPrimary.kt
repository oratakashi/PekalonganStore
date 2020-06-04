package id.oratakashi.pekalonganstore.data.model.addresses.setprimary

import com.google.gson.annotations.SerializedName

data class ResponseAddressSetPrimary (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?
)