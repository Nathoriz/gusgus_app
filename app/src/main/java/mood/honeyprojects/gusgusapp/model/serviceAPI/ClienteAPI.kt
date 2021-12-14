package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteAPI {

    @POST( "cliente/registrar" )
    fun RegistrarClient( @Body cliente: Cliente): Call<Cliente>

    @POST( "cliente/{id}" )
    fun BuscarCliente( @Path( "id" ) id: Long ): Call<Cliente>

    @PUT( "cliente/update" )
    fun UpdateCliente( @Body cliente: Cliente ): Call<Cliente>
}