package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Producto(
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("urlimg") val urlimg: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("categoria") val categoria: Categoria?,
    @SerializedName("precio") val precio: Double?,
    @SerializedName("estado") val estado: Estado?,
    @SerializedName("visibilidad") val visibilidad: Visibilidad?
)