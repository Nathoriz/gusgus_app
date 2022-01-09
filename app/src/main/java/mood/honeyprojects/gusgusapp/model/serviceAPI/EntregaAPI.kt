package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Entrega
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EntregaAPI {

    @POST( "entrega/guardar" )
    fun GuardarEntrega( @Body entrega: Entrega ): Call<Entrega>
}