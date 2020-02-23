package id.oratakashi.pekalonganstore.data.model.stores.create

import com.google.gson.annotations.SerializedName

data class DataStoresCreate (
    @SerializedName("id") val id : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("description") val description : String?,
    @SerializedName("user_id") val user_id : String?,
    @SerializedName("subdistrict_id") val subdistrict_id : String?
)