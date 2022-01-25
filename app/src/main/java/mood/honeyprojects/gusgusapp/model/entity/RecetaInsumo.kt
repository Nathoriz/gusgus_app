package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class RecetaInsumo (
    @SerializedName("id") val id: Long?,
    @SerializedName("receta") val receta: Receta?,
    @SerializedName("insumo") val insumo: Insumo?,
    @SerializedName("cantidadUso") val cantidadUso: String?
)