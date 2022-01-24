package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Cubierta
import retrofit2.Call
import retrofit2.http.*

interface CubiertaAPI {
    @GET( "cubierta/listar" )
    fun listarCubierta(): Call<List<Cubierta>>

    @POST( "cubierta/guardar" )
    fun guardarCubierta( @Body cubierta: Cubierta): Call<Cubierta>

    @PUT( "cubierta/actualizar" )
    fun actualizarCubierta( @Body cubierta: Cubierta): Call<String>

    @GET( "cubierta/{id}" )
    fun buscarCubierta( @Path("id" ) id: Long ): Call<Cubierta>

    @DELETE( "cubierta/eliminar/{id}" )
    fun eliminarCubierta( @Path( "id" ) id: Long ): Call<String>

    @GET( "cubierta/list" )
    fun GetCubiertaByNombre( @Query( "nombre" ) nombre: String ): Call<Cubierta>
}