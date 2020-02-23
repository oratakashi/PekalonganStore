package id.oratakashi.pekalonganstore.data.model.stores.create

import com.google.gson.annotations.SerializedName

data class ResponseStoreCreate (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : DataStoresCreate
)