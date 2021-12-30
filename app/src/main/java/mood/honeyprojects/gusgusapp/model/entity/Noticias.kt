package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Noticias(
    @SerializedName( "id" ) val id: Long?,
    @SerializedName( "nombre" ) val nombre: String?,
    @SerializedName( "imgurl" ) val imgurl: String?,
    @SerializedName( "fechaCreacion" ) val fechaCreación: String?,
    @SerializedName( "observacion" ) val observacion: String?,
    @SerializedName( "visibilidad" ) val visibilidad: Visibilidad
)