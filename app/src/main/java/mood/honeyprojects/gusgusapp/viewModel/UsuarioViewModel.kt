package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Usuario
import mood.honeyprojects.gusgusapp.model.requestEntity.*
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

    val messageResponseValiPassword = MutableLiveData<ValiPassword>()
    val messageResponseUpdatePassword = MutableLiveData<ValiPassword>()

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
                            Preferences.constantes.saveClientWholeName( data.usuario.cliente?.nombre.toString() + " " +  data.usuario.cliente?.apellido.toString())
                            Preferences.constantes.saveClientName(data.usuario.cliente?.nombre.toString());
                            Preferences.constantes.saveClientLastname(data.usuario.cliente?.apellido.toString());
                            Preferences.constantes.saveTelefonoUser( data.usuario.cliente?.telefono.toString() )
                            data.usuario.cliente?.id?.let { Preferences.constantes.saveIDCliente(it) }
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

            }
        } )
    }
    fun ValidarPassword( password: PasswordResponseVali ){
        val response = RetrofitHelper.getRetrofit().create( UsuarioAPI::class.java ).ValidarPassword( password )
        response.enqueue( object: Callback<ValiPassword> {
            override fun onResponse(call: Call<ValiPassword>, response: Response<ValiPassword>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponseValiPassword.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageErrorLiveData.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }
            override fun onFailure(call: Call<ValiPassword>, t: Throwable) {
            }
        })
    }
    fun UpdatePassword( password: PasswordResponseUpdate ){
        val response = RetrofitHelper.getRetrofit().create( UsuarioAPI::class.java ).UpdatePassword( password )
        response.enqueue( object: Callback<ValiPassword> {
            override fun onResponse(call: Call<ValiPassword>, response: Response<ValiPassword>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponseUpdatePassword.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageErrorLiveData.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }
            override fun onFailure(call: Call<ValiPassword>, t: Throwable) {

            }
        })
    }
    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}