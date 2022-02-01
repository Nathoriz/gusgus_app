package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.ProductoAltura
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoAlturaAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoAlturaViewModel: ViewModel() {
    val listaProductoAlturaLiveData = MutableLiveData<List<ProductoAltura>>()
    val productoAlturaLiveData = MutableLiveData<ProductoAltura>()
    val messageResponse = MutableLiveData<String>()

    fun listarProductoAltura(id:Long){
        val response = RetrofitHelper.getRetrofit().create( ProductoAlturaAPI::class.java ).listarProductoAltura(id)
        response.enqueue( object: Callback<List<ProductoAltura>> {
            override fun onResponse(call: Call<List<ProductoAltura>>, response: Response<List<ProductoAltura>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoAlturaLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductoAltura>>, t: Throwable) {

            }
        })
    }
    fun guardarProductoAltura( productoAltura: ProductoAltura){
        val response = RetrofitHelper.getRetrofit().create( ProductoAlturaAPI::class.java ).guardarProductoAltura( productoAltura )
        response.enqueue( object: Callback<ProductoAltura> {
            override fun onResponse(call: Call<ProductoAltura>, response: Response<ProductoAltura>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        productoAlturaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<ProductoAltura>, t: Throwable) {

            }
        })
    }
    fun actualizarProductoAltura( productoAltura: ProductoAltura){
        val response = RetrofitHelper.getRetrofit().create( ProductoAlturaAPI::class.java ).actualizarProductoAltura( productoAltura )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Producto Altura actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProductoAltura( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoAlturaAPI::class.java ).buscarProductoAltura( id )
        response.enqueue( object: Callback<ProductoAltura> {
            override fun onResponse(call: Call<ProductoAltura>, response: Response<ProductoAltura>) {
                response.body()?.let {
                    if(response.code()==200){
                        productoAlturaLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProductoAltura>, t: Throwable) {

            }
        }) }
    fun eliminarProductoAltura( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProductoAlturaAPI::class.java).eliminarProductoAltura(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Producto Altura eliminado")
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