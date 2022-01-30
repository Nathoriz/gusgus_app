package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.ProductoDiametro
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoDiametroAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoDiametroViewModel: ViewModel() {
    val listaProductoDiametroLiveData = MutableLiveData<List<ProductoDiametro>>()
    val productoDiametroLiveData = MutableLiveData<ProductoDiametro>()
    val messageResponse = MutableLiveData<String>()

    fun listarProductoDiametro(id:Long){
        val response = RetrofitHelper.getRetrofit().create( ProductoDiametroAPI::class.java ).listarProductoDiametro(id)
        response.enqueue( object: Callback<List<ProductoDiametro>> {
            override fun onResponse(call: Call<List<ProductoDiametro>>, response: Response<List<ProductoDiametro>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaProductoDiametroLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<ProductoDiametro>>, t: Throwable) {

            }
        })
    }
    fun guardarProductoDiametro( productoDiametro: ProductoDiametro){
        val response = RetrofitHelper.getRetrofit().create( ProductoDiametroAPI::class.java ).guardarProductoDiametro( productoDiametro )
        response.enqueue( object: Callback<ProductoDiametro> {
            override fun onResponse(call: Call<ProductoDiametro>, response: Response<ProductoDiametro>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        productoDiametroLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<ProductoDiametro>, t: Throwable) {

            }
        })
    }
    fun actualizarProductoDiametro( productoDiametro: ProductoDiametro){
        val response = RetrofitHelper.getRetrofit().create( ProductoDiametroAPI::class.java ).actualizarProductoDiametro( productoDiametro )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( "Producto Diametro actualizado" )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun buscarProductoDiametro( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoDiametroAPI::class.java ).buscarProductoDiametro( id )
        response.enqueue( object: Callback<ProductoDiametro> {
            override fun onResponse(call: Call<ProductoDiametro>, response: Response<ProductoDiametro>) {
                response.body()?.let {
                    if(response.code()==200){
                        productoDiametroLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<ProductoDiametro>, t: Throwable) {

            }
        }) }
    fun eliminarProductoDiametro( id:Long ){
        val response = RetrofitHelper.getRetrofit().create(ProductoDiametroAPI::class.java).eliminarProductoDiametro(id)
        response.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if(response.code()==200){
                        messageResponse.postValue("Producto Diametro eliminado")
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