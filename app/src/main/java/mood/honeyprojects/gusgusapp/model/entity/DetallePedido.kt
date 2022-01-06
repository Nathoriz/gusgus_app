package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class DetallePedido (
    @SerializedName("id") val id: Long?,
    @SerializedName("pedido" ) val pedido: Pedido?,
    @SerializedName("producto" ) val producto: Producto?,
    @SerializedName("personalizacion" ) val personalizacion: Personalizacion?,
    @SerializedName("cantidad") val cantidad: Int?,
    @SerializedName("subtotal") val subtotal: Double?

    )