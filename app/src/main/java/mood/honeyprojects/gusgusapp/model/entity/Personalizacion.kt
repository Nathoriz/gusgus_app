package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Personalizacion (
        @SerializedName("id") val id: Long?,
        @SerializedName("categoria") val categoria: Categoria?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("descripcion") val descripcion: String?,
        @SerializedName("urlimg") val urlimg: String?,
        @SerializedName("visibilidad") val visibilidad: Visibilidad?,
        @SerializedName("precio") val precio: Double?


        )