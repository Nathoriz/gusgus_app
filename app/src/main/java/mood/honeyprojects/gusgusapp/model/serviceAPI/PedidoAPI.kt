package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Pedido
import retrofit2.Call
import retrofit2.http.*

interface PedidoAPI {

    @POST( "pedidos/registrar" )
    fun RegistrarPedido( @Body pedido: Pedido ): Call<Pedido>

    @GET( "pedidos/cliente/{id}" )
    fun ListarPedidoAll( @Path( "id" ) id: Long ): Call<List<Pedido>>

    @GET( "pedidos/estado/{nombre}/{id}" )
    fun ListarByEstadoAndId( @Path( "nombre" ) nombre: String, @Path( "id" ) id: Long ): Call<List<Pedido>>

    @GET( "pedidos/cliente/{id}/{estado}/pedidoid" )
    fun FindPedidosClientsById(
        @Path( "id" ) id: Long,
        @Path( "estado" ) estado: String,
        @Query( "pedidoid" ) pedidoid: String ): Call<List<Pedido>>

    @GET( "pedidos/{id}" )
    fun FindPedidoByID( @Path( "id" ) id: Long ): Call<Pedido>
}