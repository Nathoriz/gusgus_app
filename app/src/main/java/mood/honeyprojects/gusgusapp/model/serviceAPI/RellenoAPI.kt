package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Relleno
import retrofit2.Call
import retrofit2.http.*

interface RellenoAPI {
    @GET( "relleno/listar" )
    fun listarRelleno(): Call<List<Relleno>>

    @POST( "relleno/guardar" )
    fun guardarRelleno( @Body relleno: Relleno): Call<Relleno>

    @PUT( "relleno/actualizar" )
    fun actualizarRelleno( @Body relleno: Relleno): Call<String>

    @GET( "relleno/{id}" )
    fun buscarRelleno( @Path("id" ) id: Long ): Call<Relleno>

    @DELETE( "relleno/eliminar/{id}" )
    fun eliminarRelleno( @Path( "id" ) id: Long ): Call<String>

    @GET( "relleno/list" )
    fun getByDescripcion( @Query( "descripcion" ) descripcion: String ): Call<Relleno>
}