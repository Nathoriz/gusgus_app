package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class PersonalizacionPiso (
    @SerializedName("id") val id: Long?,
    @SerializedName("personalizacion" ) val personalizacion: Personalizacion?,
    @SerializedName("piso" ) val piso: Piso?,
    @SerializedName("sabor" ) val sabor: Sabor?,
    @SerializedName("relleno" ) val relleno: Relleno?,
    @SerializedName("diametro" ) val diametro: Diametro?,
    @SerializedName("precio" ) val precio: Double?
    )