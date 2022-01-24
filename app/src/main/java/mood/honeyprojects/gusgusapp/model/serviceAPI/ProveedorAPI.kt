package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import retrofit2.Call
import retrofit2.http.*

interface ProveedorAPI {
    @GET( "proveedor/listar" )
    fun listarProveedor(): Call<List<Proveedor>>

    @POST( "proveedor/registrar" )
    fun guardarProveedor( @Body proveedor: Proveedor): Call<Proveedor>

    @PUT( "proveedor/actualizar" )
    fun actualizarProveedor( @Body proveedor: Proveedor): Call<String>

    @GET( "proveedor/{id}" )
    fun buscarProveedor( @Path("id" ) id: Long ): Call<Proveedor>

    @DELETE( "proveedor/eliminar/{id}" )
    fun eliminarProveedor( @Path( "id" ) id: Long ): Call<String>
}