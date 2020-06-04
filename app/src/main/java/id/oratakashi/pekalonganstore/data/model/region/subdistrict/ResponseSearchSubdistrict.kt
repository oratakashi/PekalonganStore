package id.oratakashi.pekalonganstore.data.model.region.subdistrict

import com.google.gson.annotations.SerializedName
import id.oratakashi.pekalonganstore.data.model.region.subdistrict.DataSearchSubdistrict

data class ResponseSearchSubdistrict (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?,
    @SerializedName("data") val data : List<DataSearchSubdistrict>?
)