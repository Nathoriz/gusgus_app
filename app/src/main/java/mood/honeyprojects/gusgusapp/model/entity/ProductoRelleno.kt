package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class ProductoRelleno (
    @SerializedName("id") val id: Long?,
    @SerializedName("relleno") val relleno: Relleno?,
    @SerializedName("producto") val producto: Producto?


        )