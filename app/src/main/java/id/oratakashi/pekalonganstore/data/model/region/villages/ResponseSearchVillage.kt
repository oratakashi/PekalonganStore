package id.oratakashi.pekalonganstore.data.model.region.villages

import com.google.gson.annotations.SerializedName

data class ResponseSearchVillage (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : List<DataSearchVillage>?
)