package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Entrega
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EntregaAPI {

    @POST( "entrega/guardar" )
    fun GuardarEntrega( @Body entrega: Entrega ): Call<Entrega>

    @GET( "entrega/{id}" )
    fun FindEntregaById( @Path( "id" ) id: Long ): Call<Entrega>
}