package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Diametro (
    @SerializedName("id") val id: Long?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName( "precio" ) var precio: String?
    )