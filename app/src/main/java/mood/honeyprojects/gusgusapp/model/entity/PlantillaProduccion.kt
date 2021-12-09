package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class PlantillaProduccion (
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("tiempoProduccion") val tiempoProduccion: Double?,
    @SerializedName("costoProduccion") val costoProduccion: Double?
    )