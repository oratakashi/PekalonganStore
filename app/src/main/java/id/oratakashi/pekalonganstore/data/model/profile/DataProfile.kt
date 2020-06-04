package id.oratakashi.pekalonganstore.data.model.profile

import com.google.gson.annotations.SerializedName

data class DataProfile (
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("email") val email : String?,
    @SerializedName("phone") val phone : String?,
    @SerializedName("photo") val photo : String?,
    @SerializedName("updated_at") val updated_at : String?,
    @SerializedName("address") val address : String?,
    @SerializedName("villages") val villages : DataVillages?
)