package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad

class NoticiaUpdate(
    @SerializedName( "id" ) val id: Long?,
    @SerializedName( "nombre" ) val nombre: String?,
    @SerializedName( "imgurl" ) val imgurl: String?,
    @SerializedName( "observacion" ) val observacion: String?,
    @SerializedName( "visibilidad" ) val visibilidad: Visibilidad?
)