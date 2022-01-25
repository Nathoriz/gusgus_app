package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Proveedor (
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("telefono") val telefono: String?
        )