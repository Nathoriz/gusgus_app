package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class PlantillaProduccionInsumo (
    @SerializedName("id") val id: Long?,
    @SerializedName("plantillaProduccion") val plantillaProduccion: PlantillaProduccion?,
    @SerializedName("insumo") val insumo: Insumo?,
    @SerializedName("tipoUnidad") val tipoUnidad: String?,
    @SerializedName("cantidadUso") val cantidadUso: Int?,
    @SerializedName("costo") val costo: Double?



    )