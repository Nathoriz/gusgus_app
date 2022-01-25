package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.RecetaInsumo
import retrofit2.Call
import retrofit2.http.*

interface RecetaInsumoAPI {
    @GET( "recetainsumo/listar/{recetaId}" )
    fun listarRecetaInsumo(@Path("recetaId" )id: Long): Call<List<RecetaInsumo>>

    @POST( "recetainsumo/registrar" )
    fun guardarRecetaInsumo( @Body recetaInsumo: RecetaInsumo): Call<RecetaInsumo>

    @PUT( "recetainsumo/actualizar" )
    fun actualizarRecetaInsumo( @Body recetaInsumo: RecetaInsumo): Call<String>

    @GET( "recetainsumo/{id}" )
    fun buscarRecetaInsumo( @Path("id" ) id: Long ): Call<RecetaInsumo>

    @DELETE( "recetainsumo/eliminar/{id}" )
    fun eliminarRecetaInsumo( @Path( "id" ) id: Long ): Call<String>
}