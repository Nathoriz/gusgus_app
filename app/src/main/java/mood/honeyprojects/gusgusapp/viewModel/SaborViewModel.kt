package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.model.serviceAPI.SaborAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaborViewModel: ViewModel() {
    val listaNombreSabor = MutableLiveData<List<String>>()
    val listaSaborLiveData = MutableLiveData<List<Sabor>>()
    val saborLiveData = MutableLiveData<Sabor>()
    val messageResponse = MutableLiveData<String>()

    fun listarNombreSabor(){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).listarSabor()
        response.enqueue( object: Callback<List<Sabor>> {
            override fun onResponse(call: Call<List<Sabor>>, response: Response<List<Sabor>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var lista = mutableListOf<String>()
                        for( dark in it ){
                            lista.add( dark.nombre.toString() )
                        }
                        listaNombreSabor.postValue( lista )
                    }
                }
            }
            override fun onFailure(call: Call<List<Sabor>>, t: Throwable) {
            }
        })
    }
    fun listarSabor(){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).listarSabor()
        response.enqueue( object: Callback<List<Sabor>> {
            override fun onResponse(call: Call<List<Sabor>>, response: Response<List<Sabor>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaSaborLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Sabor>>, t: Throwable) {

            }
        })
    }
    fun guardarSabor( sabor: Sabor){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).guardarSabor( sabor )
        response.enqueue( object: Callback<Sabor> {
            override fun onResponse(call: Call<Sabor>, response: Response<Sabor>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        saborLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Sabor>, t: Throwable) {

            }
        })
    }
    fun actualizarSabor( sabor: Sabor){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).actualizarSabor( sabor )
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
    fun buscarSabor( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).buscarSabor( id )
        response.enqueue( object: Callback<Sabor> {
            override fun onResponse(call: Call<Sabor>, response: Response<Sabor>) {
                response.body()?.let {
                    if(response.code()==200){
                        saborLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Sabor>, t: Throwable) {

            }
        }) }
    fun eliminarSabor( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(SaborAPI::class.java).eliminarSabor(id)
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
    fun BuscarSaborNombre( nombre: String ){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).FindByNombre( nombre )
        response.enqueue( object: Callback<Sabor> {
            override fun onResponse(call: Call<Sabor>, response: Response<Sabor>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        saborLiveData.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Sabor>, t: Throwable) {
            }
        })
    }

    private fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }


}