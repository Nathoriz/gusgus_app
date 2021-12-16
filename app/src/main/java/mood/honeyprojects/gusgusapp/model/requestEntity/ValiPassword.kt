package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class ValiPassword(
        @SerializedName("estado") val estado: Boolean?,
        @SerializedName("message") val message: String?
)