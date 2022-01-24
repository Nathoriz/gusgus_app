package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Personalizacion
import mood.honeyprojects.gusgusapp.model.serviceAPI.PersonalizacionAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalizacionViewModel: ViewModel() {
    val responsePersonalizacion = MutableLiveData<Personalizacion>()
    val responseMessage = MutableLiveData<String>()

    fun RegistrarPeronalizacion( personali: Personalizacion ){
        val response = RetrofitHelper.getRetrofit().create( PersonalizacionAPI::class.java ).RegistrarPersonalizacion( personali )
        response.enqueue( object: Callback<Personalizacion> {
            override fun onResponse(call: Call<Personalizacion>, response: Response<Personalizacion>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responsePersonalizacion.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Personalizacion>, t: Throwable) {
            }
        })
    }
    fun ActualizarPrecio( id: Long, precio: Double ){
        val response = RetrofitHelper.getRetrofit().create( PersonalizacionAPI::class.java ).UpdatePrecio( id, precio )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseMessage.value = it
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
}