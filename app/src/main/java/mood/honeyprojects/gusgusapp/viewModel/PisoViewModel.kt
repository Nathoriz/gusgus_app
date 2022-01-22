package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Piso
import mood.honeyprojects.gusgusapp.model.serviceAPI.PisoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PisoViewModel: ViewModel() {
    val responsePiso = MutableLiveData<List<Piso>>()

    fun getPisos(){
        val response = RetrofitHelper.getRetrofit().create( PisoAPI::class.java ).getPisos()
        response.enqueue( object: Callback<List<Piso>> {
            override fun onResponse(call: Call<List<Piso>>, response: Response<List<Piso>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responsePiso.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<List<Piso>>, t: Throwable) {
            }
        })
    }
}