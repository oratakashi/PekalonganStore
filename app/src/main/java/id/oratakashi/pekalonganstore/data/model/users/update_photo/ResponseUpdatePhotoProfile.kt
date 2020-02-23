package id.oratakashi.pekalonganstore.data.model.users.update_photo

import com.google.gson.annotations.SerializedName

data class ResponseUpdatePhotoProfile (
    @SerializedName("status") var status : Boolean?,
    @SerializedName("message") var message : String?,
    @SerializedName("data") var data : DataUpdatePhotoProfile?
)