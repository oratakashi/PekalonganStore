package id.oratakashi.pekalonganstore.data.model.profile

import com.google.gson.annotations.SerializedName

data class DataVillages (
    @SerializedName("village_id") val village_id : String?,
    @SerializedName("village_name") val village_name : String?,
    @SerializedName("district_id") val district_id : String?,
    @SerializedName("district_name") val district_name : String?,
    @SerializedName("regency_id") val regency_id : String?,
    @SerializedName("regency_name") val regency_name : String?,
    @SerializedName("province_id") val province_id : String?,
    @SerializedName("province_name") val province_name : String?
)