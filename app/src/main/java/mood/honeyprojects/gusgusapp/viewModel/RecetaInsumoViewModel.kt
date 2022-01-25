package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.RecetaInsumo
import mood.honeyprojects.gusgusapp.model.serviceAPI.RecetaInsumoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecetaInsumoViewModel: ViewModel() {
    val listaRecetaInsumoLiveData = MutableLiveData<List<RecetaInsumo>>()
    val recetaInsumoLiveData = MutableLiveData<RecetaInsumo>()
    val messageResponse = MutableLiveData<String>()

    fun listarRecetaInsumo(id:Long){
        val response = RetrofitHelper.getRetrofit().create( RecetaInsumoAPI::class.java ).listarRecetaInsumo(id)
        response.enqueue( object: Callback<List<RecetaInsumo>> {
            override fun onResponse(call: Call<List<RecetaInsumo>>, response: Response<List<RecetaInsumo>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaRecetaInsumoLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<RecetaInsumo>>, t: Throwable) {

            }
        })
    }
    fun guardarRecetaInsumo( recetaInsumo: RecetaInsumo){
        val response = RetrofitHelper.getRetrofit().create( RecetaInsumoAPI::class.java ).guardarRecetaInsumo( recetaInsumo )
        response.enqueue( object: Callback<RecetaInsumo> {
            override fun onResponse(call: Call<RecetaInsumo>, response: Response<RecetaInsumo>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        recetaInsumoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<RecetaInsumo>, t: Throwable) {

            }
        })
    }
    fun actualizarRecetaInsumo( recetaInsumo: RecetaInsumo){
        val response = RetrofitHelper.getRetrofit().create( RecetaInsumoAPI::class.java ).actualizarRecetaInsumo( recetaInsumo )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Insumo actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarRecetaInsumo( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( RecetaInsumoAPI::class.java ).buscarRecetaInsumo( id )
        response.enqueue( object: Callback<RecetaInsumo> {
            override fun onResponse(call: Call<RecetaInsumo>, response: Response<RecetaInsumo>) {
                response.body()?.let {
                    if(response.code()==200){
                        recetaInsumoLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<RecetaInsumo>, t: Throwable) {

            }
        }) }
    fun eliminarRecetaInsumo( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(RecetaInsumoAPI::class.java).eliminarRecetaInsumo(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Insumo eliminado")
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