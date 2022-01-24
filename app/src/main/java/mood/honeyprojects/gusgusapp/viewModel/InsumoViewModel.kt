package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Insumo
import mood.honeyprojects.gusgusapp.model.requestEntity.InsumoResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.InsumoUpdate
import mood.honeyprojects.gusgusapp.model.serviceAPI.InsumoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsumoViewModel: ViewModel(){
    val listaInsumoLiveData = MutableLiveData<List<Insumo>>()
    val insumoLiveDate = MutableLiveData<Insumo>()
    val messageResponse = MutableLiveData<String>()

    fun listarInsumo(){
        val response = RetrofitHelper.getRetrofit().create( InsumoAPI::class.java ).listarInsumo()
        response.enqueue( object: Callback<List<Insumo>>{
            override fun onResponse(call: Call<List<Insumo>>, response: Response<List<Insumo>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaInsumoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( it.string() )
                    }
                }
            }
            override fun onFailure(call: Call<List<Insumo>>, t: Throwable) {
            }
        } )
    }
    fun guardarInsumo( insumo: InsumoResponse){
        val response = RetrofitHelper.getRetrofit().create( InsumoAPI::class.java ).guardarInsumo( insumo )
        response.enqueue( object: Callback<Insumo> {
            override fun onResponse(call: Call<Insumo>, response: Response<Insumo>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        insumoLiveDate.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Insumo>, t: Throwable) {

            }
        })
    }
    fun actualizarInsumo( insumo: InsumoUpdate){
        val response = RetrofitHelper.getRetrofit().create( InsumoAPI::class.java ).actualizarInsumo( insumo )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarInsumo( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( InsumoAPI::class.java ).buscarInsumo( id )
        response.enqueue( object: Callback<Insumo> {
            override fun onResponse(call: Call<Insumo>, response: Response<Insumo>) {
                response.body()?.let {
                    if(response.code()==200){
                        insumoLiveDate.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Insumo>, t: Throwable) {

            }

        }) }
    fun eliminarInsumo( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(InsumoAPI::class.java).eliminarInsumo(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("eliminado")
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }

    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }

}