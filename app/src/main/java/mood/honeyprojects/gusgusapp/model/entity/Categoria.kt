package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad

class Categoria(
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String,
        @SerializedName("urlimg") val urlimg: String,
        @SerializedName("visibilidad") val visibilidad: Visibilidad?
)