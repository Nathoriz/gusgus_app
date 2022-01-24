package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Cubierta
import mood.honeyprojects.gusgusapp.model.serviceAPI.CubiertaAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CubiertaViewModel:ViewModel() {
    val listaNombreCubierta = MutableLiveData<List<String>>()
    val listaCubiertaLiveData = MutableLiveData<List<Cubierta>>()
    val cubiertaLiveData = MutableLiveData<Cubierta>()
    val messageResponse = MutableLiveData<String>()

    fun listarNombreCubierta(){
        val response = RetrofitHelper.getRetrofit().create( CubiertaAPI::class.java ).listarCubierta()
        response.enqueue( object: Callback<List<Cubierta>> {
            override fun onResponse(call: Call<List<Cubierta>>, response: Response<List<Cubierta>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var lista = mutableListOf<String>()
                        for( dark in it ){
                            lista.add( dark.nombre.toString() )
                        }
                        listaNombreCubierta.postValue( lista )
                    }
                }
            }
            override fun onFailure(call: Call<List<Cubierta>>, t: Throwable) {
            }
        })
    }
    fun listarCubierta(){
        val response = RetrofitHelper.getRetrofit().create( CubiertaAPI::class.java ).listarCubierta()
        response.enqueue( object: Callback<List<Cubierta>> {
            override fun onResponse(call: Call<List<Cubierta>>, response: Response<List<Cubierta>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaCubiertaLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Cubierta>>, t: Throwable) {

            }
        })
    }
    fun guardarCubierta( cubierta: Cubierta){
        val response = RetrofitHelper.getRetrofit().create( CubiertaAPI::class.java ).guardarCubierta( cubierta )
        response.enqueue( object: Callback<Cubierta> {
            override fun onResponse(call: Call<Cubierta>, response: Response<Cubierta>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        cubiertaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Cubierta>, t: Throwable) {

            }
        })
    }
    fun actualizarCubierta( cubierta: Cubierta){
        val response = RetrofitHelper.getRetrofit().create( CubiertaAPI::class.java ).actualizarCubierta( cubierta )
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
    fun buscarCubierta( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( CubiertaAPI::class.java ).buscarCubierta( id )
        response.enqueue( object: Callback<Cubierta> {
            override fun onResponse(call: Call<Cubierta>, response: Response<Cubierta>) {
                response.body()?.let {
                    if(response.code()==200){
                        cubiertaLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Cubierta>, t: Throwable) {

            }
        }) }
    fun eliminarCubierta( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(CubiertaAPI::class.java).eliminarCubierta(id)
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
    fun GetCubiertaByNombre( nombre: String ){
        val response = RetrofitHelper.getRetrofit().create( CubiertaAPI::class.java ).GetCubiertaByNombre( nombre )
        response.enqueue( object: Callback<Cubierta> {
            override fun onResponse(call: Call<Cubierta>, response: Response<Cubierta>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        cubiertaLiveData.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Cubierta>, t: Throwable) {
            }
        })
    }
    private fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}