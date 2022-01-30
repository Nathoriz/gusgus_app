package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.ProductoCubierta
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoCubiertaAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoCubiertaViewModel: ViewModel() {
    val listaProductoCubiertaLiveData = MutableLiveData<List<ProductoCubierta>>()
    val productoCubiertaLiveData = MutableLiveData<ProductoCubierta>()
    val messageResponse = MutableLiveData<String>()

    fun listarProductoCubierta(id:Long){
        val response = RetrofitHelper.getRetrofit().create( ProductoCubiertaAPI::class.java ).listarProductoCubierta(id)
        response.enqueue( object: Callback<List<ProductoCubierta>> {
            override fun onResponse(call: Call<List<ProductoCubierta>>, response: Response<List<ProductoCubierta>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoCubiertaLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductoCubierta>>, t: Throwable) {

            }
        })
    }
    fun guardarProductoCubierta( productoCubierta: ProductoCubierta){
        val response = RetrofitHelper.getRetrofit().create( ProductoCubiertaAPI::class.java ).guardarProductoCubierta( productoCubierta )
        response.enqueue( object: Callback<ProductoCubierta> {
            override fun onResponse(call: Call<ProductoCubierta>, response: Response<ProductoCubierta>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        productoCubiertaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<ProductoCubierta>, t: Throwable) {

            }
        })
    }
    fun actualizarProductoCubierta( productoCubierta: ProductoCubierta){
        val response = RetrofitHelper.getRetrofit().create( ProductoCubiertaAPI::class.java ).actualizarProductoCubierta( productoCubierta )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Producto Cubierta actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProductoCubierta( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoCubiertaAPI::class.java ).buscarProductoCubierta( id )
        response.enqueue( object: Callback<ProductoCubierta> {
            override fun onResponse(call: Call<ProductoCubierta>, response: Response<ProductoCubierta>) {
                response.body()?.let {
                    if(response.code()==200){
                        productoCubiertaLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProductoCubierta>, t: Throwable) {

            }
        }) }
    fun eliminarProductoCubierta( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProductoCubiertaAPI::class.java).eliminarProductoCubierta(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Producto Cubierta eliminado")
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