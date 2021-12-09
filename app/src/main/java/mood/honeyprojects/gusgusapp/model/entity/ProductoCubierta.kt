package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class ProductoCubierta (
    @SerializedName("id") val id: Long?,
    @SerializedName("cubierta") val cubierta: Cubierta?,
    @SerializedName("producto") val producto: Producto?


    )