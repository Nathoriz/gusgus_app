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
}