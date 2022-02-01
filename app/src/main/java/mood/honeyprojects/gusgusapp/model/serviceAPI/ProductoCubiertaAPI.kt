package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.ProductoCubierta
import retrofit2.Call
import retrofit2.http.*

interface ProductoCubiertaAPI {
    @GET( "productocubierta/listar/{productoid}" )
    fun listarProductoCubierta(@Path("productoid" ) id: Long): Call<List<ProductoCubierta>>

    @POST( "productocubierta/guardar" )
    fun guardarProductoCubierta( @Body productoCubierta: ProductoCubierta): Call<ProductoCubierta>

    @PUT( "productocubierta/actualizar" )
    fun actualizarProductoCubierta( @Body productoCubierta: ProductoCubierta): Call<String>

    @GET( "productocubierta/{id}" )
    fun buscarProductoCubierta( @Path("id" ) id: Long ): Call<ProductoCubierta>

    @DELETE( "productocubierta/eliminar/{id}" )
    fun eliminarProductoCubierta( @Path( "id" ) id: Long ): Call<String>
}