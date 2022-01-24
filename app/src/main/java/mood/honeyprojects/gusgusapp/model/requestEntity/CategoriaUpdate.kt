package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad

class CategoriaUpdate (
    @SerializedName("id") var id: Long?,
    @SerializedName("nombre") var nombre: String,
    @SerializedName("imgnombre") var imgnombre: String,
    @SerializedName("urlimg") var urlimg: String,
    @SerializedName("visibilidad") var visibilidad: Visibilidad?
)