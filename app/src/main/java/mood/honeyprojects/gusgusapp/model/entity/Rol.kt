package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Rol(
        @SerializedName("id") val id: Long?,
        @SerializedName("tiporol") val tiporol: String?
)