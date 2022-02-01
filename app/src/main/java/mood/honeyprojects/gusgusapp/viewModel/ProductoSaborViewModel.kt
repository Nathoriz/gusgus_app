package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.ProductoSabor
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoSaborAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoSaborViewModel: ViewModel() {
    val listaProductoSaborLiveData = MutableLiveData<List<ProductoSabor>>()
    val productoSaborLiveData = MutableLiveData<ProductoSabor>()
    val messageResponse = MutableLiveData<String>()

    fun listarProductoSabor(id:Long){
        val response = RetrofitHelper.getRetrofit().create( ProductoSaborAPI::class.java ).listarProductoSabor(id)
        response.enqueue( object: Callback<List<ProductoSabor>> {
            override fun onResponse(call: Call<List<ProductoSabor>>, response: Response<List<ProductoSabor>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoSaborLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductoSabor>>, t: Throwable) {

            }
        })
    }
    fun guardarProductoSabor( productoSabor: ProductoSabor){
        val response = RetrofitHelper.getRetrofit().create( ProductoSaborAPI::class.java ).guardarProductoSabor( productoSabor )
        response.enqueue( object: Callback<ProductoSabor> {
            override fun onResponse(call: Call<ProductoSabor>, response: Response<ProductoSabor>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        productoSaborLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<ProductoSabor>, t: Throwable) {

            }
        })
    }
    fun actualizarProductoSabor( productoSabor: ProductoSabor){
        val response = RetrofitHelper.getRetrofit().create( ProductoSaborAPI::class.java ).actualizarProductoSabor( productoSabor )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Producto Sabor actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProductoSabor( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoSaborAPI::class.java ).buscarProductoSabor( id )
        response.enqueue( object: Callback<ProductoSabor> {
            override fun onResponse(call: Call<ProductoSabor>, response: Response<ProductoSabor>) {
                response.body()?.let {
                    if(response.code()==200){
                        productoSaborLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProductoSabor>, t: Throwable) {

            }
        }) }
    fun eliminarProductoSabor( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProductoSaborAPI::class.java).eliminarProductoSabor(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Producto Sabor eliminado")
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