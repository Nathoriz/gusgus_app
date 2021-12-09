package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class ProductoSabor (
    @SerializedName("id") val id: Long?,
    @SerializedName("sabor") val sabor: Sabor?,
    @SerializedName("producto") val producto: Producto?

        )