package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.DetallePedido
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DetallePedidoAPI {
    @POST( "detallepedidos/registrar" )
    fun RegistrarDetaPedido( @Body detallePedido: DetallePedido ): Call<DetallePedido>
}