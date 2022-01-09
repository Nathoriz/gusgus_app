package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Entrega (
    @SerializedName("id") val id: Long?,
    @SerializedName("fecha") val fecha: String?,
    @SerializedName("distrito" ) val distrito: Distrito?,
    @SerializedName("hora") val hora: String?,
    @SerializedName( "direccion" ) val direccion: String?
    )