package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.model.entity.Relleno
import retrofit2.Call
import retrofit2.http.*

interface DistritoAPI {

    @GET( "distrito/listar" )
    fun listarDistrito(): Call<List<Distrito>>

    @GET( "/distrito/buscar" )
    fun buscarDistritoPorNombre( @Query( "nombre" ) nombre: String ): Call<Distrito>

    @POST( "distrito/guardar" )
    fun guardarDistrito( @Body distrito: Distrito): Call<Distrito>

    @PUT( "distrito/actualizar" )
    fun actualizarDistrito( @Body distrito: Distrito): Call<String>

    @GET( "distrito/{id}" )
    fun buscarDistrito( @Path("id" ) id: Long ): Call<Distrito>

    @DELETE( "distrito/eliminar/{id}" )
    fun eliminarDistrito( @Path( "id" ) id: Long ): Call<String>
}