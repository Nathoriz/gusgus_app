package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Detalle (
    @SerializedName( "id" ) val id: Long?,
    @SerializedName( "cumpleaniero" ) val cumpleaniero: String?,
    @SerializedName( "observacion" ) val observacion: String?
)