package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Admin(
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("apellido") val apellido: String?,
        @SerializedName("dni") val dni: String?
)