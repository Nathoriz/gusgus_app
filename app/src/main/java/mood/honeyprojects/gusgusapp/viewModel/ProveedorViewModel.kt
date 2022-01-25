package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import mood.honeyprojects.gusgusapp.model.serviceAPI.AlturaAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProveedorAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorViewModel : ViewModel(){
    val listaProveedorLiveData = MutableLiveData<List<Proveedor>>()
    val proveedorLiveData = MutableLiveData<Proveedor>()
    val messageResponse = MutableLiveData<String>()

    fun listarProveedor(){
        val response = RetrofitHelper.getRetrofit().create( ProveedorAPI::class.java ).listarProveedor()
        response.enqueue( object: Callback<List<Proveedor>> {
            override fun onResponse(call: Call<List<Proveedor>>, response: Response<List<Proveedor>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProveedorLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Proveedor>>, t: Throwable) {

            }
        })
    }
    fun guardarProveedor( proveedor: Proveedor){
        val response = RetrofitHelper.getRetrofit().create( ProveedorAPI::class.java ).guardarProveedor( proveedor )
        response.enqueue( object: Callback<Proveedor> {
            override fun onResponse(call: Call<Proveedor>, response: Response<Proveedor>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        proveedorLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Proveedor>, t: Throwable) {

            }
        })
    }
    fun actualizarProveedor( proveedor: Proveedor){
        val response = RetrofitHelper.getRetrofit().create( ProveedorAPI::class.java ).actualizarProveedor( proveedor )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Proveedor actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProveedor( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProveedorAPI::class.java ).buscarProveedor( id )
        response.enqueue( object: Callback<Proveedor> {
            override fun onResponse(call: Call<Proveedor>, response: Response<Proveedor>) {
                response.body()?.let {
                    if(response.code()==200){
                        proveedorLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Proveedor>, t: Throwable) {

            }
        }) }
    fun eliminarProveedor( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProveedorAPI::class.java).eliminarProveedor(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Proveedor eliminado")
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }

    private fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }


}