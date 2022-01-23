package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Altura
import retrofit2.Call
import retrofit2.http.*

interface AlturaAPI {
    @GET( "altura/listar" )
    fun listarAltura(): Call<List<Altura>>

    @POST( "altura/guardar" )
    fun guardarAltura( @Body altura: Altura): Call<Altura>

    @PUT( "altura/actualizar" )
    fun actualizarAltura( @Body altura: Altura): Call<String>

    @GET( "altura/{id}" )
    fun buscarAltura( @Path("id" ) id: Long ): Call<Altura>

    @DELETE( "altura/eliminar/{id}" )
    fun eliminarAltura( @Path( "id" ) id: Long ): Call<String>
}