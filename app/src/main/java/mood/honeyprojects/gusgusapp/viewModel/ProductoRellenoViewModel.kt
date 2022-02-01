package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.ProductoRelleno
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoRellenoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoRellenoViewModel: ViewModel() {
    val listaProductoRellenoLiveData = MutableLiveData<List<ProductoRelleno>>()
    val productoRellenoLiveData = MutableLiveData<ProductoRelleno>()
    val messageResponse = MutableLiveData<String>()

    fun listarProductoRelleno(id:Long){
        val response = RetrofitHelper.getRetrofit().create( ProductoRellenoAPI::class.java ).listarProductoRelleno(id)
        response.enqueue( object: Callback<List<ProductoRelleno>> {
            override fun onResponse(call: Call<List<ProductoRelleno>>, response: Response<List<ProductoRelleno>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoRellenoLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductoRelleno>>, t: Throwable) {

            }
        })
    }
    fun guardarProductoRelleno( productoRelleno: ProductoRelleno){
        val response = RetrofitHelper.getRetrofit().create( ProductoRellenoAPI::class.java ).guardarProductoRelleno( productoRelleno )
        response.enqueue( object: Callback<ProductoRelleno> {
            override fun onResponse(call: Call<ProductoRelleno>, response: Response<ProductoRelleno>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        productoRellenoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<ProductoRelleno>, t: Throwable) {

            }
        })
    }
    fun actualizarProductoRelleno( productoRelleno: ProductoRelleno){
        val response = RetrofitHelper.getRetrofit().create( ProductoRellenoAPI::class.java ).actualizarProductoRelleno( productoRelleno )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Producto Relleno actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProductoRelleno( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoRellenoAPI::class.java ).buscarProductoRelleno( id )
        response.enqueue( object: Callback<ProductoRelleno> {
            override fun onResponse(call: Call<ProductoRelleno>, response: Response<ProductoRelleno>) {
                response.body()?.let {
                    if(response.code()==200){
                        productoRellenoLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProductoRelleno>, t: Throwable) {

            }
        }) }
    fun eliminarProductoRelleno( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProductoRellenoAPI::class.java).eliminarProductoRelleno(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Producto Relleno eliminado")
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