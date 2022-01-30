package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.ProductoRelleno
import retrofit2.Call
import retrofit2.http.*

interface ProductoRellenoAPI {
    @GET( "productorelleno/listar/{productoid}" )
    fun listarProductoRelleno(@Path("productoid" ) id: Long): Call<List<ProductoRelleno>>

    @POST( "productorelleno/guardar" )
    fun guardarProductoRelleno( @Body productoRelleno: ProductoRelleno): Call<ProductoRelleno>

    @PUT( "productorelleno/actualizar" )
    fun actualizarProductoRelleno( @Body productoRelleno: ProductoRelleno): Call<String>

    @GET( "productorelleno/{id}" )
    fun buscarProductoRelleno( @Path("id" ) id: Long ): Call<ProductoRelleno>

    @DELETE( "productorelleno/eliminar/{id}" )
    fun eliminarProductoRelleno( @Path( "id" ) id: Long ): Call<String>
}