package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class DetalleProductoResponse(
    @SerializedName("detalle") val detalle: Detalle?
)