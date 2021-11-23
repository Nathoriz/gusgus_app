package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Usuario
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioClient
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioLogin
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioAPI {
    @POST( "usuario/registrarUsuario" )
    fun RegistrarUsuarioClient( @Body cliente: UsuarioClient ): Call<Usuario>

    @POST( "usuario/login" )
    fun LoginUsuario( @Body usuario: UsuarioLogin ): Call<UsuarioResponse>
}