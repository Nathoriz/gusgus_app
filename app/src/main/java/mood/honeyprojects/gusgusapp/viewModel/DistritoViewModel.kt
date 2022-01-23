package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.model.serviceAPI.AlturaAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.DistritoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistritoViewModel: ViewModel() {

    val listaNombreDistrito = MutableLiveData<List<String>>()
    val listaDistritoLiveData = MutableLiveData<List<Distrito>>()
    val distritoLiveData = MutableLiveData<Distrito>()
    val messageResponse = MutableLiveData<String>()

    fun listarNombreDistrito(){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).listarDistrito()
        response.enqueue( object: Callback<List<Distrito>> {
            override fun onResponse(call: Call<List<Distrito>>, response: Response<List<Distrito>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var list = mutableListOf<String>()
                        for( dart in it ){
                            list.add(dart.nombre.toString())
                        }
                        listaNombreDistrito.postValue( list )
                    }
                }
            }
            override fun onFailure(call: Call<List<Distrito>>, t: Throwable) {
            }
        })
    }
    fun buscarDistritoPorNombre( nombre: String ){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).buscarDistritoPorNombre( nombre )
        response.enqueue( object:Callback<Distrito> {
            override fun onResponse(call: Call<Distrito>, response: Response<Distrito>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        distritoLiveData.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Distrito>, t: Throwable) {
            }
        })
    }
    fun listarDistrito(){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).listarDistrito()
        response.enqueue( object: Callback<List<Distrito>> {
            override fun onResponse(call: Call<List<Distrito>>, response: Response<List<Distrito>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaDistritoLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Distrito>>, t: Throwable) {

            }
        })
    }
    fun guardarDistrito( distrito: Distrito){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).guardarDistrito( distrito )
        response.enqueue( object: Callback<Distrito> {
            override fun onResponse(call: Call<Distrito>, response: Response<Distrito>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        distritoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Distrito>, t: Throwable) {

            }
        })
    }
    fun actualizarDistrito( distrito: Distrito){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).actualizarDistrito( distrito )
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
    fun buscarDistrito( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).buscarDistrito( id )
        response.enqueue( object: Callback<Distrito> {
            override fun onResponse(call: Call<Distrito>, response: Response<Distrito>) {
                response.body()?.let {
                    if(response.code()==200){
                        distritoLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Distrito>, t: Throwable) {

            }
        }) }
    fun eliminarDistrito( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(DistritoAPI::class.java).eliminarDistrito(id)
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