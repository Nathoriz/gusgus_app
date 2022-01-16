package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Pedido
import mood.honeyprojects.gusgusapp.model.serviceAPI.PedidoAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoViewModel: ViewModel() {
    val responsePedidoLiveData = MutableLiveData<Pedido>()
    val responseMessage = MutableLiveData<String>()

    fun RegistrarPedido( pedido: Pedido ){
        val response = RetrofitHelper.getRetrofit().create( PedidoAPI::class.java ).RegistrarPedido( pedido )
        response.enqueue( object: Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responsePedidoLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseMessage.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
            }
        } )
    }
    private fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}