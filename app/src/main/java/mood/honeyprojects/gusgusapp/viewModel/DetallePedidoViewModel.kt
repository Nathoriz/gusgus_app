package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.serviceAPI.DetallePedidoAPI
import mood.honeyprojects.gusgusapp.model.entity.DetallePedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetallePedidoViewModel: ViewModel() {
    val responseDePedido = MutableLiveData<DetallePedido>()
    val responseDePedidoList = MutableLiveData<List<DetallePedido>>()

    fun RegistrarDetallePedido( detalePedi: DetallePedido ){
        val response = RetrofitHelper.getRetrofit().create( DetallePedidoAPI::class.java ).RegistrarDetaPedido( detalePedi )
        response.enqueue( object:Callback<DetallePedido> {
            override fun onResponse(call: Call<DetallePedido>, response: Response<DetallePedido>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseDePedido.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<DetallePedido>, t: Throwable) {
            }
        })
    }
    fun GetListByPedidoId( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( DetallePedidoAPI::class.java ).GetDetalleByPedidoId( id )
        response.enqueue( object: Callback<List<DetallePedido>> {
            override fun onResponse(call: Call<List<DetallePedido>>, response: Response<List<DetallePedido>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseDePedidoList.value = it
                    }
                }
            }
            override fun onFailure(call: Call<List<DetallePedido>>, t: Throwable) {
            }
        })
    }
}