package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class PasswordResponseUpdate(
        @SerializedName("id") val id: Long?,
        @SerializedName("password") val password: String?,
        @SerializedName("confirmpassword") val confirmpassword: String?
)