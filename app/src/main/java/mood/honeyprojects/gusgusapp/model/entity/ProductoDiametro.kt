package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class ProductoDiametro (
    @SerializedName("id") val id: Long?,
    @SerializedName("diametro") val diametro: Diametro?,
    @SerializedName("producto") val producto: Producto?


        )