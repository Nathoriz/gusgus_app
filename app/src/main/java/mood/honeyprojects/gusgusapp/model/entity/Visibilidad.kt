package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Visibilidad(
        @SerializedName("id") val id: Long?,
        @SerializedName("visible") val visible: Boolean?
)