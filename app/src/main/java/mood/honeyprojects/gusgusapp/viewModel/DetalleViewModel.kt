package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Detalle
import mood.honeyprojects.gusgusapp.model.serviceAPI.DetalleAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalleViewModel: ViewModel() {
    val MessageDetalle = MutableLiveData<String>()

    fun RegistrarDetalle( detalle: Detalle ){
        val response = RetrofitHelper.getRetrofit().create( DetalleAPI::class.java ).RegistrarDetalle( detalle )
        response.enqueue( object: Callback<Detalle> {
            override fun onResponse(call: Call<Detalle>, response: Response<Detalle>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        MessageDetalle.postValue( "Detalle Enviado." )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        MessageDetalle.value = getErrorMessage( it.string() )
                    }
                }
            }
            override fun onFailure(call: Call<Detalle>, t: Throwable) {
            }
        })
    }
    private fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}