package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Receta (
    @SerializedName("id") val id: Long?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("tiempoProduccion") val tiempoProduccion: Double?,
    @SerializedName("costoProduccion") val costoProduccion: Double?
    )