package id.oratakashi.pekalonganstore.data.model.region

import com.google.gson.annotations.SerializedName

data class DataSearchSubdistrict (
    @SerializedName("subdistrict_id") val subdistrict_id : String?,
    @SerializedName("province_id") val province_id : String?,
    @SerializedName("province") val province : String?,
    @SerializedName("city_id") val city_id : String?,
    @SerializedName("city") val city : String?,
    @SerializedName("type") val type : String?,
    @SerializedName("subdistrict_name") val subdistrict_name : String?
)