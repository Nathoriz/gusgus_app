package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class InsumoResponse: Serializable {
    @SerializedName("nombre") var nombre: String? = null
    @SerializedName("imgnombre") var imgnombre: String? = null
    @SerializedName("img") var img: String? = null

    constructor(){}
    constructor(nombre:String?,imgnombre:String?,img:String?){
        this.nombre = nombre
        this.imgnombre = imgnombre
        this.img = img
    }
}