package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class ProveedorInsumo (
    @SerializedName("id") val id: Long?,
    @SerializedName("proveedor") val proveedor: Proveedor?,
    @SerializedName("insumo") val insumo: Insumo?,
    @SerializedName("precio") val precio: Double?
)