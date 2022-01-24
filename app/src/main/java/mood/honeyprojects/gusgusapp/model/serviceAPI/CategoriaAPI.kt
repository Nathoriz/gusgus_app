package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaUpdate
import retrofit2.Call
import retrofit2.http.*

interface CategoriaAPI {

    @GET( "categoria/listar" )
    fun listarCategoria(): Call<List<Categoria>>

    @POST( "categoria/guardar" )
    fun guardarCategoria( @Body categoria: CategoriaResponse): Call<Categoria>

    @PUT( "categoria/actualizar" )
    fun actualizarCategoria( @Body categoria: CategoriaUpdate): Call<String>

    @GET( "categoria/{id}" )
    fun buscarCategoria( @Query( "id" ) id: Long ): Call<Categoria>

    @DELETE( "categoria/eliminar/{id}" )
    fun eliminarCategoria( @Path( "id" ) id: Long ): Call<String>
}