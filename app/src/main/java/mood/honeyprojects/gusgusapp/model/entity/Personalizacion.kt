package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Personalizacion (
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("urlimg") val urlimg: String?,
        @SerializedName("cubierta") val cubierta: Cubierta?,
        @SerializedName("precio") val precio: Double?
        )