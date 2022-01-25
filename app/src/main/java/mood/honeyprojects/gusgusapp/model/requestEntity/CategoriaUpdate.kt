package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad

class CategoriaUpdate (
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("imgnombre") val imgnombre: String,
    @SerializedName("urlimg") val urlimg: String,
    @SerializedName("visibilidad") val visibilidad: Visibilidad?
)