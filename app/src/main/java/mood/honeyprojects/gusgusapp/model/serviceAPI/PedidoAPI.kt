package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Pedido
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PedidoAPI {

    @POST( "pedidos/registrar" )
    fun RegistrarPedido( @Body pedido: Pedido ): Call<Pedido>
}