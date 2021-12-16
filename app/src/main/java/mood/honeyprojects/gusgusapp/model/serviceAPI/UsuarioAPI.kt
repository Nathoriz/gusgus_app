package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Usuario
import mood.honeyprojects.gusgusapp.model.requestEntity.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsuarioAPI {
    @POST( "usuario/registrarUsuario" )
    fun RegistrarUsuarioClient( @Body cliente: UsuarioClient ): Call<Usuario>

    @POST( "usuario/login" )
    fun LoginUsuario( @Body usuario: UsuarioLogin ): Call<UsuarioResponse>

    @POST( "usuario/validar" )
    fun ValidarPassword( @Body password: PasswordResponseVali ): Call<ValiPassword>

    @PUT( "usuario/update" )
    fun UpdatePassword( @Body password: PasswordResponseUpdate ): Call<ValiPassword>
}