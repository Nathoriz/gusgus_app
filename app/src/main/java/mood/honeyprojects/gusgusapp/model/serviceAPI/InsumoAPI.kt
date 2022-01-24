package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Insumo
import mood.honeyprojects.gusgusapp.model.requestEntity.InsumoResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.InsumoUpdate
import retrofit2.Call
import retrofit2.http.*

interface InsumoAPI {
    @GET( "insumo/listar" )
    fun listarInsumo(): Call<List<Insumo>>

    @POST( "insumo/guardar" )
    fun guardarInsumo( @Body insumo: InsumoResponse): Call<Insumo>

    @PUT( "insumo/actualizar" )
    fun actualizarInsumo( @Body insumo: InsumoUpdate): Call<String>

    @GET( "insumo/{id}" )
    fun buscarInsumo( @Path( "id" ) id: Long ): Call<Insumo>

    @DELETE( "insumo/eliminar/{id}" )
    fun eliminarInsumo( @Path( "id" ) id: Long ): Call<String>
}