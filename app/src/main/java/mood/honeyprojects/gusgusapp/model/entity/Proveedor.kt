package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Proveedor (
        @SerializedName("id") val id: Long?,
        @SerializedName("nombre") val nombre: String?,
        @SerializedName("distrito") val distrito: Distrito?,
        @SerializedName("direccion") val direccion: String?,
        @SerializedName("telefono") val telefono: String?



        )