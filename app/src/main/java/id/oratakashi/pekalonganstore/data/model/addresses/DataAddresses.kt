package id.oratakashi.pekalonganstore.data.model.addresses

import com.google.gson.annotations.SerializedName

data class DataAddresses (
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("receiver_name") val receiver_name : String?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("street") val street : String?,
    @SerializedName("default") val default : String?,
    @SerializedName("village") val village : DataVillages?
)