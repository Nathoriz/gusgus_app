package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Detalle (
    @SerializedName( "id" ) val id: Long?,
    @SerializedName( "frase" ) val cumpleaniero: String?,
    @SerializedName( "observacion" ) val observacion: String?,
    @SerializedName( "pedido" ) val pedido: Pedido?
)