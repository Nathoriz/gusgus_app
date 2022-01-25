package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.entity.Insumo
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaUpdate
import mood.honeyprojects.gusgusapp.model.serviceAPI.AlturaAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.CategoriaAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.InsumoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaViewModel: ViewModel() {
    val listaCategoriaLiveData = MutableLiveData<List<Categoria>>()
    val categoriaLiveDate = MutableLiveData<Categoria>()
    val messageResponse = MutableLiveData<String>()
    val listaNombreCategoria = MutableLiveData<List<String>>()

    fun listarNombreCategoria(){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).listarCategoria()
        response.enqueue( object: Callback<List<Categoria>>{
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var list = mutableListOf<String>()
                        for( dart in it ){
                            list.add(dart.nombre.toString())
                        }
                        listaNombreCategoria.postValue( list )
                    }
                }
            }
            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
            }
        } )
    }

    fun listarCategoria(){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).listarCategoria()
        response.enqueue( object: Callback<List<Categoria>>{
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaCategoriaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( it.string() )
                    }
                }
            }
            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
            }
        } )
    }
    fun guardarCategoria( categoria: CategoriaResponse){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).guardarCategoria( categoria )
        response.enqueue( object: Callback<Categoria> {
            override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        categoriaLiveDate.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Categoria>, t: Throwable) {

            }
        })
    }
    fun actualizarCategoria( categoria: CategoriaUpdate){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).actualizarCategoria( categoria )
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
    fun buscarCategoria( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).buscarCategoria(id)
        response.enqueue( object: Callback<Categoria> {
            override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                response.body()?.let {
                    if(response.code()==200){
                        categoriaLiveDate.postValue(it)
                    }
                }
            }
            override fun onFailure(call: Call<Categoria>, t: Throwable) {

            }

        }) }
    fun eliminarCategoria( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(CategoriaAPI::class.java).eliminarCategoria(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Categoria eliminada")
                    }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })
    }
    fun buscarPorNombreCategoria( nombre: String ){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).buscarPorNombreCategoria( nombre )
        response.enqueue( object: Callback<Categoria> {
            override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        categoriaLiveDate.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Categoria>, t: Throwable) {
            }
        })
    }


    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}