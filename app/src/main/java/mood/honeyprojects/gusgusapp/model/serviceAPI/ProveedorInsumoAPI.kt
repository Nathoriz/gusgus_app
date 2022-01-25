package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.ProveedorInsumo
import retrofit2.Call
import retrofit2.http.*

interface ProveedorInsumoAPI {
    @GET( "proveedorinsumo/listar/{proveedorid}" )
    fun listarProveedorInsumo(@Path("proveedorid" ) id: Long): Call<List<ProveedorInsumo>>

    @POST( "proveedorinsumo/registrar" )
    fun guardarProveedorInsumo( @Body proveedorinsumo: ProveedorInsumo): Call<ProveedorInsumo>

    @PUT( "proveedorinsumo/actualizar" )
    fun actualizarProveedorInsumo( @Body proveedorinsumo: ProveedorInsumo): Call<String>

    @GET( "proveedorinsumo/{id}" )
    fun buscarProveedorInsumo( @Path("id" ) id: Long ): Call<ProveedorInsumo>

    @DELETE( "proveedorinsumo/eliminar/{id}" )
    fun eliminarProveedorInsumo( @Path( "id" ) id: Long ): Call<String>
}