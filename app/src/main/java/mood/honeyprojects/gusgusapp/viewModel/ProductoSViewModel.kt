package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoSViewModel: ViewModel() {
    val listaProductoLiveData = MutableLiveData<List<Producto>?>()
    val responseMensaje = MutableLiveData<String>()

    fun FiltroProducto( nombre: String ){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).FiltroProducto( nombre )
        response.enqueue( object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 404 ){
                        responseMensaje.postValue( "Not Found" )
                    }
                }
            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
            }
        })
    }
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
}