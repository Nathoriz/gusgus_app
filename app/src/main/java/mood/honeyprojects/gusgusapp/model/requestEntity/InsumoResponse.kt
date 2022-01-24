package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class InsumoResponse: Serializable {
    @SerializedName("nombre") var nombre: String? = null
    @SerializedName("img") var img: String? = null

    constructor(){}
    constructor(nombre:String?,img:String?){
        this.nombre = nombre
        this.img = img
    }
}