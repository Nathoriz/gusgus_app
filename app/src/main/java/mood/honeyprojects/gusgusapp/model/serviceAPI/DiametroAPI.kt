package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Diametro
import retrofit2.Call
import retrofit2.http.*

interface DiametroAPI {
    @GET( "diametro/listar" )
    fun listarDiametro(): Call<List<Diametro>>

    @POST( "diametro/guardar" )
    fun guardarDiametro( @Body diametro: Diametro): Call<Diametro>

    @PUT( "diametro/actualizar" )
    fun actualizarDiametro( @Body diametro: Diametro): Call<String>

    @GET( "diametro/{id}" )
    fun buscarDiametro( @Path("id" ) id: Long ): Call<Diametro>

    @DELETE( "diametro/eliminar/{id}" )
    fun eliminarDiametro( @Path( "id" ) id: Long ): Call<String>
}