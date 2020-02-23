package id.oratakashi.pekalonganstore.data.model.region

import com.google.gson.annotations.SerializedName

data class ResponseSearchSubdistrict (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : List<DataSearchSubdistrict>?
)