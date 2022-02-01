package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.entity.Receta
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import java.io.Serializable

class ProductoResponse: Serializable {
    @SerializedName("nombre") var nombre: String? = null
    @SerializedName("urlimg") var urlimg: String? = null
    @SerializedName("descripcion") var descripcion: String? = null
    @SerializedName("categoria") var categoria: Categoria? = null
    @SerializedName("precio") var precio: Double? = null
    @SerializedName("receta") var receta: Receta? = null
    @SerializedName("visibilidad") var visibilidad: Visibilidad? = null

    constructor(){}
    constructor(nombre: String?, urlimg: String?, descripcion: String?, categoria: Categoria?, precio: Double?, receta: Receta?, visibilidad: Visibilidad?) {
        this.nombre = nombre
        this.urlimg = urlimg
        this.descripcion = descripcion
        this.categoria = categoria
        this.precio = precio
        this.receta = receta
        this.visibilidad = visibilidad
    }
}