package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Usuario
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioLogin
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioResponse
import mood.honeyprojects.gusgusapp.model.serviceAPI.UsuarioAPI
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuarioViewModel: ViewModel() {
    //LiveData
    val messageLiveData = MutableLiveData<String>()
    val messageErrorLiveData = MutableLiveData<String>()

    fun Login( usuarioLogin: UsuarioLogin ){
        val response = RetrofitHelper.getRetrofit().create( UsuarioAPI::class.java ).LoginUsuario( usuarioLogin )
        response.enqueue( object: Callback<UsuarioResponse>{
            override fun onResponse(call: Call<UsuarioResponse>, response: Response<UsuarioResponse>) {
                response.body()?.let { data ->
                    if( response.code() == 200 ){
                        if( data.usuario?.rol?.tiporol == "ADMIN" ){
                            Preferences.constantes.saveRole( data.usuario.rol.tiporol )
                            Preferences.constantes.saveAdminName( data.usuario.admin?.nombre.toString() )
                            Preferences.constantes.saveBoolean( true )
                            messageLiveData.postValue( data.usuario.rol.tiporol.toString() )
                        }else if( data.usuario?.rol?.tiporol == "CLIENTE" ){
                            Preferences.constantes.saveRole( data.usuario.rol.tiporol )
                            Preferences.constantes.saveClientName( data.usuario.cliente?.nombreCompleto.toString() )
                            Preferences.constantes.saveTelefonoUser( data.usuario.cliente?.telefono.toString() )
                            Preferences.constantes.saveDireccion( data.usuario.cliente?.direccion.toString() )
                            data.usuario.cliente?.id?.let { Preferences.constantes.saveIDCliente(it) }
                            Preferences.constantes.saveDistrito( data.usuario.cliente?.distrito?.nombre!! )
                            Preferences.constantes.saveBoolean( true )
                            messageLiveData.postValue( data.usuario.rol.tiporol.toString() )
                        }
                    }
                }
                response.errorBody()?.let {
                    messageErrorLiveData.postValue( getErrorMessage(it.string()) )
                }
            }
            override fun onFailure(call: Call<UsuarioResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        } )
    }
    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}