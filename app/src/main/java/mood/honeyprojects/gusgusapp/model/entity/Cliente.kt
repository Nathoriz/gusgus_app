package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Cliente(
        @SerializedName("id") val id: Long?,
        @SerializedName("nombreCompleto") val nombreCompleto: String?,
        @SerializedName("direccion") val direccion: String?,
        @SerializedName("telefono") val telefono: String?
)