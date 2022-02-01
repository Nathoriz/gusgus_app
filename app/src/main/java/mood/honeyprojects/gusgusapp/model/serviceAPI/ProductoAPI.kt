package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import mood.honeyprojects.gusgusapp.model.requestEntity.DetalleProductoResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.ProductoResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.ProductoUpdate
import retrofit2.Call
import retrofit2.http.*

interface ProductoAPI {

    @GET( "producto/true/listar" )
    fun ListProducto(): Call<List<Producto>>

    @GET( "producto/true/categoria/listar" )
    fun ListarProductPorCategoria( @Query("categoria") categoria: String ): Call<List<Producto>>

    @GET( "producto/true/filtro" )
    fun FiltroProducto( @Query("nombre") nombre: String ): Call<List<Producto>>

    @GET( "producto/filtro" )
    fun FiltroTodosProducto( @Query("nombre") nombre: String ): Call<List<Producto>>

    @GET( "producto/{id}" )
    fun DetalleProducto( @Path("id") id: Long? ): Call<DetalleProductoResponse>

    @GET( "producto/buscar/{id}" )
    fun buscarProducto( @Path("id") id: Long? ): Call<Producto>

    @GET( "producto/find/{id}" )
    fun ListForIdProduct( @Path( "id" ) id: Long? ): Call<List<Producto>>


    @GET( "producto/listar" )
    fun listarTodosProducto(): Call<List<Producto>>

    @POST( "producto/registrar" )
    fun guardarProducto( @Body producto: ProductoResponse): Call<Producto>

    @PUT( "producto/actualizar" )
    fun actualizarProducto( @Body producto: ProductoUpdate): Call<String>

    @DELETE( "producto/eliminar/{id}" )
    fun eliminarProducto( @Path( "id" ) id: Long ): Call<String>
}