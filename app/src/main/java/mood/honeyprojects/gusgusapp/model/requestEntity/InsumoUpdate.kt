package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class InsumoUpdate (
    @SerializedName("id") val id: Long?,
    @SerializedName("nombre") val nombre: String?,
    @SerializedName("imgnombre") val imgnombre: String?,
    @SerializedName("img") val img: String?
)