package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import mood.honeyprojects.gusgusapp.model.serviceAPI.VisibilidadAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VisibilidadViewModel: ViewModel() {

    val visibilidadLiveData = MutableLiveData<Visibilidad>()

    fun GetVisibilidad( boolean: Boolean ){
        val response = RetrofitHelper.getRetrofit().create( VisibilidadAPI::class.java ).getVisibilidad( boolean )
        response.enqueue( object: Callback<Visibilidad> {
            override fun onResponse(call: Call<Visibilidad>, response: Response<Visibilidad>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        visibilidadLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<Visibilidad>, t: Throwable) {
            }
        })
    }
}