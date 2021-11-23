package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.listeners.ValiRegisterClient
import mood.honeyprojects.gusgusapp.listeners.ValiRegisterUsuario
import mood.honeyprojects.gusgusapp.model.entity.Cliente
import mood.honeyprojects.gusgusapp.model.entity.Usuario
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioClient
import mood.honeyprojects.gusgusapp.model.serviceAPI.ClienteAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.UsuarioAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ClienteViewModel: ViewModel() {
    //LiveData
    val responseLiveData = MutableLiveData<String>()

    fun RegistrarClient( client: Cliente, valiCliente: ValiRegisterClient ){
        val response = RetrofitHelper.getRetrofit().create( ClienteAPI::class.java ).RegistrarClient( client )
        response.enqueue( object: Callback<Cliente> {
            override fun onResponse(call: Call<Cliente>, response: Response<Cliente>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseLiveData.postValue( "Cliente Registrado." )
                        valiCliente.ValiCliente( true )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseLiveData.postValue( getErrorMessage(it.string()) )
                        valiCliente.ValiCliente( false )
                    }
                }
            }
            override fun onFailure(call: Call<Cliente>, t: Throwable) {

            }

        })
    }
    fun RegistrarUsuario( cliente: UsuarioClient, valiUsuario: ValiRegisterUsuario ){
        val response = RetrofitHelper.getRetrofit().create( UsuarioAPI::class.java ).RegistrarUsuarioClient( cliente )
        response.enqueue( object: Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseLiveData.postValue( "Usuario Registrado." )
                        valiUsuario.ValiUsuario( true )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseLiveData.postValue( getErrorMessage(it.string()) )
                        valiUsuario.ValiUsuario( false )
                    }
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}