package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Pedido(
    @SerializedName("id") val id: Long?,
    @SerializedName("cliente") val cliente: Cliente?,
    @SerializedName("entrega") val entrega: Entrega?,
    @SerializedName("fechaCreación") val fechaCreación: String?,
    @SerializedName("estado") val estado: Estado?

    )