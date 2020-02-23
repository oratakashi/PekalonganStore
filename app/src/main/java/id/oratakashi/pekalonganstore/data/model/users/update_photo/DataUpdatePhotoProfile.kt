package id.oratakashi.pekalonganstore.data.model.users.update_photo

import com.google.gson.annotations.SerializedName

data class DataUpdatePhotoProfile (
    @SerializedName("filename") var filename : String?,
    @SerializedName("url") var url : String?
)