package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import mood.honeyprojects.gusgusapp.model.requestEntity.DetalleProductoResponse
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProveedorAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class ProductoViewModel: ViewModel() {

    val listaProductoLiveData = MutableLiveData<List<Producto>>()
    val productoLiveData = MutableLiveData<Producto>()
    val responseMensaje = MutableLiveData<String>()

    fun ListarProducts(){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).ListProducto()
        response.enqueue( object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseMensaje.postValue( "No Hay Lista de Productos.." )
                    }
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
            }
        })
    }
    fun ListarProductsPorCategoria( categoria: String ){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).ListarProductPorCategoria( categoria )
        response.enqueue( object: Callback<List<Producto>>{
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseMensaje.postValue( it.string() )
                    }
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {

            }
        } )
    }
    fun ListForIdProduct( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).ListForIdProduct( id )
        response.enqueue( object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoLiveData.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
            }
        })
    }

    fun listarTodosProducto(){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).listarTodosProducto()
        response.enqueue( object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {

            }
        })
    }
    fun guardarProducto( producto: Producto){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).guardarProducto( producto )
        response.enqueue( object: Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        productoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseMensaje.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {

            }
        })
    }
    fun actualizarProducto( producto: Producto){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).actualizarProducto( producto )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseMensaje.postValue( "Producto actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun eliminarProducto( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProductoAPI::class.java).eliminarProducto(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        responseMensaje.postValue("Producto eliminado")
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