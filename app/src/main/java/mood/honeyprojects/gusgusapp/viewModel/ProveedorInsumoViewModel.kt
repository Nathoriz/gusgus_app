package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.ProveedorInsumo
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProveedorInsumoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProveedorInsumoViewModel : ViewModel() {
    val listaProveedorInsumoLiveData = MutableLiveData<List<ProveedorInsumo>>()
    val proveedorInsumoLiveData = MutableLiveData<ProveedorInsumo>()
    val messageResponse = MutableLiveData<String>()

    fun listarProveedorInsumo(id:Long){
        val response = RetrofitHelper.getRetrofit().create( ProveedorInsumoAPI::class.java ).listarProveedorInsumo(id)
        response.enqueue( object: Callback<List<ProveedorInsumo>> {
            override fun onResponse(call: Call<List<ProveedorInsumo>>, response: Response<List<ProveedorInsumo>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProveedorInsumoLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<ProveedorInsumo>>, t: Throwable) {

            }
        })
    }
    fun guardarProveedorInsumo( proveedorInsumo: ProveedorInsumo){
        val response = RetrofitHelper.getRetrofit().create( ProveedorInsumoAPI::class.java ).guardarProveedorInsumo( proveedorInsumo )
        response.enqueue( object: Callback<ProveedorInsumo> {
            override fun onResponse(call: Call<ProveedorInsumo>, response: Response<ProveedorInsumo>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        proveedorInsumoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<ProveedorInsumo>, t: Throwable) {

            }
        })
    }
    fun actualizarProveedorInsumo( proveedorInsumo: ProveedorInsumo){
        val response = RetrofitHelper.getRetrofit().create( ProveedorInsumoAPI::class.java ).actualizarProveedorInsumo( proveedorInsumo )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "ProveedorInsumo actualizada" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProveedorInsumo( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProveedorInsumoAPI::class.java ).buscarProveedorInsumo( id )
        response.enqueue( object: Callback<ProveedorInsumo> {
            override fun onResponse(call: Call<ProveedorInsumo>, response: Response<ProveedorInsumo>) {
                response.body()?.let {
                    if(response.code()==200){
                        proveedorInsumoLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProveedorInsumo>, t: Throwable) {

            }
        }) }
    fun eliminarProveedorInsumo( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProveedorInsumoAPI::class.java).eliminarProveedorInsumo(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("ProveedorInsumo eliminada")
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