package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import java.io.Serializable

class NoticiaResponse: Serializable {
    @SerializedName( "nombre" ) var nombre: String? = null
    @SerializedName( "imgurl" ) var imgurl: String? = null
    @SerializedName( "observacion" ) var observacion: String? = null
    @SerializedName( "visibilidad" ) var visibilidad: Visibilidad? = null

    constructor(){}
    constructor(nombre: String?, imgurl: String?, observacion: String?, visibilidad: Visibilidad?) {
        this.nombre = nombre
        this.imgurl = imgurl
        this.observacion = observacion
        this.visibilidad = visibilidad
    }
}