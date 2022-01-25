package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Receta
import mood.honeyprojects.gusgusapp.model.serviceAPI.RecetaAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecetaViewModel : ViewModel(){
    val listaRecetaLiveData = MutableLiveData<List<Receta>>()
    val recetaLiveData = MutableLiveData<Receta>()
    val messageResponse = MutableLiveData<String>()

    fun listarReceta(){
        val response = RetrofitHelper.getRetrofit().create( RecetaAPI::class.java ).listarReceta()
        response.enqueue( object: Callback<List<Receta>> {
            override fun onResponse(call: Call<List<Receta>>, response: Response<List<Receta>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaRecetaLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Receta>>, t: Throwable) {

            }
        })
    }
    fun guardarReceta( receta: Receta){
        val response = RetrofitHelper.getRetrofit().create( RecetaAPI::class.java ).guardarReceta( receta )
        response.enqueue( object: Callback<Receta> {
            override fun onResponse(call: Call<Receta>, response: Response<Receta>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        recetaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Receta>, t: Throwable) {

            }
        })
    }
    fun actualizarReceta( receta: Receta){
        val response = RetrofitHelper.getRetrofit().create( RecetaAPI::class.java ).actualizarReceta( receta )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Receta actualizada" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarReceta( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( RecetaAPI::class.java ).buscarReceta( id )
        response.enqueue( object: Callback<Receta> {
            override fun onResponse(call: Call<Receta>, response: Response<Receta>) {
                response.body()?.let {
                    if(response.code()==200){
                        recetaLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Receta>, t: Throwable) {

            }
        }) }
    fun eliminarReceta( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(RecetaAPI::class.java).eliminarReceta(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Receta eliminado")
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