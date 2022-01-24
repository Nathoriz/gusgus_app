package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Insumo (
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("imgnombre") val imgnombre: String?,
    @SerializedName("img") val img: String?
//    @SerializedName("tipoUnidad") val tipoUnidad: String?,
//    @SerializedName("costoPorUnidad") val costoPorUnidad: Double?,
//    @SerializedName("costoPorMayor") val costoPorMayor: Double?,
//    @SerializedName("cantUnidad") val cantUnidad: Int?,
//    @SerializedName( "cantMayor") val cantMayor: Int?
    )