package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.serviceAPI.AlturaAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlturaViewModel : ViewModel(){
    val listaAlturaLiveData = MutableLiveData<List<Altura>>()
    val alturaLiveData = MutableLiveData<Altura>()
    val messageResponse = MutableLiveData<String>()

    fun listarAltura(){
        val response = RetrofitHelper.getRetrofit().create( AlturaAPI::class.java ).listarAltura()
        response.enqueue( object: Callback<List<Altura>> {
            override fun onResponse(call: Call<List<Altura>>, response: Response<List<Altura>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaAlturaLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Altura>>, t: Throwable) {

            }
        })
    }
    fun guardarAltura( altura: Altura){
        val response = RetrofitHelper.getRetrofit().create( AlturaAPI::class.java ).guardarAltura( altura )
        response.enqueue( object: Callback<Altura> {
            override fun onResponse(call: Call<Altura>, response: Response<Altura>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        alturaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Altura>, t: Throwable) {

            }
        })
    }
    fun actualizarAltura( altura: Altura){
        val response = RetrofitHelper.getRetrofit().create( AlturaAPI::class.java ).actualizarAltura( altura )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Altura actualizada" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarAltura( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( AlturaAPI::class.java ).buscarAltura( id )
        response.enqueue( object: Callback<Altura> {
            override fun onResponse(call: Call<Altura>, response: Response<Altura>) {
                response.body()?.let {
                    if(response.code()==200){
                        alturaLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Altura>, t: Throwable) {

            }
        }) }
    fun eliminarAltura( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(AlturaAPI::class.java).eliminarAltura(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Altura eliminada")
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