package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.ProductoSabor
import retrofit2.Call
import retrofit2.http.*

interface ProductoSaborAPI {
    @GET( "productosabor/listar/{productoid}" )
    fun listarProductoSabor(@Path("productoid" ) id: Long): Call<List<ProductoSabor>>

    @POST( "productosabor/guardar" )
    fun guardarProductoSabor( @Body productoSabor: ProductoSabor): Call<ProductoSabor>

    @PUT( "productosabor/actualizar" )
    fun actualizarProductoSabor( @Body productoSabor: ProductoSabor): Call<String>

    @GET( "productosabor/{id}" )
    fun buscarProductoSabor( @Path("id" ) id: Long ): Call<ProductoSabor>

    @DELETE( "productosabor/eliminar/{id}" )
    fun eliminarProductoSabor( @Path( "id" ) id: Long ): Call<String>
}