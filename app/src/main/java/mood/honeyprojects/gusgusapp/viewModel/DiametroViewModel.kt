package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.serviceAPI.AlturaAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.DiametroAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiametroViewModel: ViewModel() {
    val listaNombreDiametro = MutableLiveData<List<String>>()
    val listaDiametroLiveData = MutableLiveData<List<Diametro>>()
    val diametroLiveData = MutableLiveData<Diametro>()
    val messageResponse = MutableLiveData<String>()

    fun listarNombreDiametro(){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).listarDiametro()
        response.enqueue( object: Callback<List<Diametro>> {
            override fun onResponse(call: Call<List<Diametro>>, response: Response<List<Diametro>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var lista = mutableListOf<String>()
                        for( dark in it ){
                            lista.add( dark.descripcion.toString() )
                        }
                        listaNombreDiametro.postValue( lista )
                    }
                }
            }
            override fun onFailure(call: Call<List<Diametro>>, t: Throwable) {
            }
        })
    }
    fun listarDiametro(){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).listarDiametro()
        response.enqueue( object: Callback<List<Diametro>> {
            override fun onResponse(call: Call<List<Diametro>>, response: Response<List<Diametro>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaDiametroLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Diametro>>, t: Throwable) {

            }
        })
    }
    fun guardarDiametro( diametro: Diametro){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).guardarDiametro( diametro )
        response.enqueue( object: Callback<Diametro> {
            override fun onResponse(call: Call<Diametro>, response: Response<Diametro>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        diametroLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Diametro>, t: Throwable) {

            }
        })
    }
    fun actualizarDiametro( diametro: Diametro){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).actualizarDiametro( diametro )
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
    fun buscarDiametro( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).buscarDiametro( id )
        response.enqueue( object: Callback<Diametro> {
            override fun onResponse(call: Call<Diametro>, response: Response<Diametro>) {
                response.body()?.let {
                    if(response.code()==200){
                        diametroLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<Diametro>, t: Throwable) {

            }
        }) }
    fun eliminarDiametro( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(DiametroAPI::class.java).eliminarDiametro(id)
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
    fun GetDiametroByDescrip( descripcion: String ){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).getByDescripcion( descripcion )
        response.enqueue( object: Callback<Diametro> {
            override fun onResponse(call: Call<Diametro>, response: Response<Diametro>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        diametroLiveData.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Diametro>, t: Throwable) {
            }
        })
    }

    private fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}