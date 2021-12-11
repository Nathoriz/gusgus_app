package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.requestEntity.DetalleProductoResponse
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleProductoViewModel: ViewModel() {
    val detalleProductoLiveData = MutableLiveData<DetalleProductoResponse>()
    val responseMessage = MutableLiveData<String>()

    fun DetalleProducto( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( ProductoAPI::class.java ).DetalleProducto( id )
        response.enqueue( object: Callback<DetalleProductoResponse> {
            override fun onResponse(call: Call<DetalleProductoResponse>, response: Response<DetalleProductoResponse>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        detalleProductoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseMessage.postValue( it.string() )
                    }
                }
            }
            override fun onFailure(call: Call<DetalleProductoResponse>, t: Throwable) {
            }
        } )
    }
}