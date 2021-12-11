package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.*

class Detalle(
    @SerializedName("producto") val producto: Producto?,
    @SerializedName("diametros") val diametros: List<Diametro>?,
    @SerializedName("altura") val altura: Altura?,
    @SerializedName("sabores") val sabores: List<Sabor>?,
    @SerializedName("rellenos") val rellenos: List<Relleno>?,
    @SerializedName("cubierta") val cubierta: Cubierta?
)