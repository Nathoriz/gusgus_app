package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Cliente(
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("apellido") val apellido: String?,
        @SerializedName("telefono") val telefono: String?
)