package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Noticias: Serializable {
    @SerializedName("id") var id: Long? = null
    @SerializedName("nombre") var nombre: String? = null
    @SerializedName("imgurl") var imgurl: String? = null
    @SerializedName("fechaCreacion") var fechaCreación: String? = null
    @SerializedName("observacion") var observacion: String? = null
    @SerializedName("visibilidad") var visibilidad: Visibilidad? = null

    constructor(){}
    constructor(
        id: Long?,
        nombre: String?,
        imgurl: String?,
        fechaCreación: String?,
        observacion: String?,
        visibilidad: Visibilidad?
    ) {
        this.id = id
        this.nombre = nombre
        this.imgurl = imgurl
        this.fechaCreación = fechaCreación
        this.observacion = observacion
        this.visibilidad = visibilidad
    }
}