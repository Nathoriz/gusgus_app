package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class InsumoUpdate (
    @SerializedName("id") var id: Long?,
    @SerializedName("nombre") var nombre: String?,
    @SerializedName("imgnombre") var imgnombre: String?,
    @SerializedName("img") var img: String?
)