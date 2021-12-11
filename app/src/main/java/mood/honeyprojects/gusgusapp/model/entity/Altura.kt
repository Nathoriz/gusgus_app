package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Altura(
        @SerializedName("id") val id: Long?,
        @SerializedName("descripcion")val descripcion: String?
)