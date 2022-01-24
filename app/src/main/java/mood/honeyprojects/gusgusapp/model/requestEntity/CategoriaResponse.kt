package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import java.io.Serializable

class CategoriaResponse: Serializable {
    @SerializedName("nombre") var nombre: String? = null
    @SerializedName("imgnombre") var imgnombre: String? = null
    @SerializedName("urlimg") var urlimg: String? = null
    @SerializedName( "visibilidad" ) var visibilidad: Visibilidad? = null

    constructor(){}

    constructor(nombre:String?,imgnombre:String?,urlimg:String?,visibilidad: Visibilidad?){
        this.nombre = nombre
        this.imgnombre=imgnombre
        this.urlimg = urlimg
        this.visibilidad = visibilidad
    }
}