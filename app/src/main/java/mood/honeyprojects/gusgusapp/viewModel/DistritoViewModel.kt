package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.model.serviceAPI.DistritoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DistritoViewModel: ViewModel() {

    val responseDistritoMutableLiveData = MutableLiveData<Distrito>()
    val responseString = MutableLiveData<List<String>>()

    fun ListDistrito(){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).listarDistrito()
        response.enqueue( object: Callback<List<Distrito>> {
            override fun onResponse(call: Call<List<Distrito>>, response: Response<List<Distrito>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var list = mutableListOf<String>()
                        for( dart in it ){
                            list.add(dart.nombre.toString())
                        }
                        responseString.postValue( list )
                    }
                }
            }
            override fun onFailure(call: Call<List<Distrito>>, t: Throwable) {
            }
        })
    }
    fun BuscarDistrito( nombre: String ){
        val response = RetrofitHelper.getRetrofit().create( DistritoAPI::class.java ).BuscarDistrito( nombre )
        response.enqueue( object:Callback<Distrito> {
            override fun onResponse(call: Call<Distrito>, response: Response<Distrito>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseDistritoMutableLiveData.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<Distrito>, t: Throwable) {
            }
        })
    }
}