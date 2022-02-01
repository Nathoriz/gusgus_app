package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.entity.Receta
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad

class ProductoUpdate (
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("urlimg") val urlimg: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("categoria") val categoria: Categoria?,
    @SerializedName("precio") val precio: Double?,
    @SerializedName("receta") val receta: Receta?,
    @SerializedName("visibilidad") val visibilidad: Visibilidad?
)