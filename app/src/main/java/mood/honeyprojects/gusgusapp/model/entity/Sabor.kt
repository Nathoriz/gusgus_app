package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Sabor (
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("color") val color: String?
        )