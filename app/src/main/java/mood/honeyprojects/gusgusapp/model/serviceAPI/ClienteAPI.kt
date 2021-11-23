package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Cliente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ClienteAPI {

    @POST( "cliente/registrar" )
    fun RegistrarClient( @Body cliente: Cliente): Call<Cliente>
}