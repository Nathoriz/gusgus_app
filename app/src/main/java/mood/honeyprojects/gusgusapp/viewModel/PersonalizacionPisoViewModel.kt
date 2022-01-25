package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.PersonalizacionPiso
import mood.honeyprojects.gusgusapp.model.serviceAPI.PersonalizacionPisoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalizacionPisoViewModel: ViewModel() {
    val responseDataPersPiso = MutableLiveData<PersonalizacionPiso>()
    val responseDataList = MutableLiveData<List<PersonalizacionPiso>>()

    fun RegistrarPersPiso( persPiso: PersonalizacionPiso ){
        val response = RetrofitHelper.getRetrofit().create( PersonalizacionPisoAPI::class.java ).RegistrarPersonalizacionPiso( persPiso )
        response.enqueue( object: Callback<PersonalizacionPiso> {
            override fun onResponse(call: Call<PersonalizacionPiso>, response: Response<PersonalizacionPiso>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseDataPersPiso.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<PersonalizacionPiso>, t: Throwable) {
            }
        })
    }
    fun ListByPersId( id: Long ){
       val response = RetrofitHelper.getRetrofit().create( PersonalizacionPisoAPI::class.java ).GetListByPersId( id )
        response.enqueue( object: Callback<List<PersonalizacionPiso>> {
            override fun onResponse(call: Call<List<PersonalizacionPiso>>, response: Response<List<PersonalizacionPiso>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseDataList.value = it
                    }
                }
            }
            override fun onFailure(call: Call<List<PersonalizacionPiso>>, t: Throwable) {
            }
        })
    }
}