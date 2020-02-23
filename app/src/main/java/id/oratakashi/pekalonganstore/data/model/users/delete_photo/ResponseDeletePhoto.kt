package id.oratakashi.pekalonganstore.data.model.users.delete_photo

import com.google.gson.annotations.SerializedName

data class ResponseDeletePhoto (
    @SerializedName("status") val status : Boolean?,
    @SerializedName("message") val message : String?
)