package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Relleno
import mood.honeyprojects.gusgusapp.model.serviceAPI.RellenoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RellenoViewModel: ViewModel() {
    val listaDescripcionRelleno = MutableLiveData<List<String>>()
    val listaRellenoLiveData = MutableLiveData<List<Relleno>>()
    val rellenoLiveData = MutableLiveData<Relleno>()
    val messageResponse = MutableLiveData<String>()

    fun listarNombreRelleno(){
        val response = RetrofitHelper.getRetrofit().create( RellenoAPI::class.java ).listarRelleno()
        response.enqueue( object: Callback<List<Relleno>> {
            override fun onResponse(call: Call<List<Relleno>>, response: Response<List<Relleno>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var lista = mutableListOf<String>()
                        for( dark in it ){
                            lista.add( dark.descripcion.toString() )
                        }
                        listaDescripcionRelleno.postValue( lista )
                    }
                }
            }
            override fun onFailure(call: Call<List<Relleno>>, t: Throwable) {
            }
        })
    }
    fun listarRelleno(){
        val response = RetrofitHelper.getRetrofit().create( RellenoAPI::class.java ).listarRelleno()
        response.enqueue( object: Callback<List<Relleno>> {
            override fun onResponse(call: Call<List<Relleno>>, response: Response<List<Relleno>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaRellenoLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Relleno>>, t: Throwable) {

            }
        })
    }
    fun guardarRelleno( relleno: Relleno){
        val response = RetrofitHelper.getRetrofit().create( RellenoAPI::class.java ).guardarRelleno( relleno )
        response.enqueue( object: Callback<Relleno> {
            override fun onResponse(call: Call<Relleno>, response: Response<Relleno>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        rellenoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Relleno>, t: Throwable) {

            }
        })
    }
    fun actualizarRelleno( relleno: Relleno){
        val response = RetrofitHelper.getRetrofit().create( RellenoAPI::class.java ).actualizarRelleno( relleno )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "OK" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarRelleno( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( RellenoAPI::class.java ).buscarRelleno( id )
        response.enqueue( object: Callback<Relleno> {
            override fun onResponse(call: Call<Relleno>, response: Response<Relleno>) {
                response.body()?.let {
                    if(response.code()==200){
                        rellenoLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Relleno>, t: Throwable) {

            }
        }) }
    fun eliminarRelleno( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(RellenoAPI::class.java).eliminarRelleno(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Eliminado")
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