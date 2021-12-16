package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class PasswordResponseVali(
        @SerializedName("id") val id: Long?,
        @SerializedName("password") val password: String?
)