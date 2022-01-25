package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.DetallePedido
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DetallePedidoAPI {
    @POST( "detallepedidos/registrar" )
    fun RegistrarDetaPedido( @Body detallePedido: DetallePedido ): Call<DetallePedido>

    @GET( "detallepedidos/pedido/{id}" )
    fun GetDetalleByPedidoId( @Path( "id" ) id: Long ): Call<List<DetallePedido>>
}