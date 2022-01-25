package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Receta
import retrofit2.Call
import retrofit2.http.*

interface RecetaAPI {
    @GET( "receta/listar" )
    fun listarReceta(): Call<List<Receta>>

    @POST( "receta/registrar" )
    fun guardarReceta( @Body receta: Receta): Call<Receta>

    @PUT( "receta/actualizar" )
    fun actualizarReceta( @Body receta: Receta): Call<String>

    @GET( "receta/{id}" )
    fun buscarReceta( @Path("id" ) id: Long ): Call<Receta>

    @DELETE( "receta/eliminar/{id}" )
    fun eliminarReceta( @Path( "id" ) id: Long ): Call<String>

    @GET( "receta/buscar" )
    fun buscarPorNombreReceta(@Query( "nombre" ) nombre: String): Call<Receta>
}