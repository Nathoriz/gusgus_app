package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class ProductoAltura (
    @SerializedName("id") val id: Long?,
    @SerializedName("altura") val altura: Altura?,
    @SerializedName("producto") val producto: Producto?



    )