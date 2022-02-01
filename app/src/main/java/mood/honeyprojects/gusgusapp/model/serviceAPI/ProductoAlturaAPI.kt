package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.ProductoAltura
import retrofit2.Call
import retrofit2.http.*

interface ProductoAlturaAPI {
    @GET( "productoaltura/listar/{productoid}" )
    fun listarProductoAltura(@Path("productoid" ) id: Long): Call<List<ProductoAltura>>

    @POST( "productoaltura/guardar" )
    fun guardarProductoAltura( @Body productoAltura: ProductoAltura): Call<ProductoAltura>

    @PUT( "productoaltura/actualizar" )
    fun actualizarProductoAltura( @Body productoAltura: ProductoAltura): Call<String>

    @GET( "productoaltura/{id}" )
    fun buscarProductoAltura( @Path("id" ) id: Long ): Call<ProductoAltura>

    @DELETE( "productoaltura/eliminar/{id}" )
    fun eliminarProductoAltura( @Path( "id" ) id: Long ): Call<String>
}