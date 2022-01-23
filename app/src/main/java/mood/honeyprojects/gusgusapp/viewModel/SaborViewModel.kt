package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.model.serviceAPI.SaborAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SaborViewModel: ViewModel() {
    val responseNombre = MutableLiveData<List<String>>()

    fun GetAllSabor(){
        val response = RetrofitHelper.getRetrofit().create( SaborAPI::class.java ).listarSabor()
        response.enqueue( object: Callback<List<Sabor>> {
            override fun onResponse(call: Call<List<Sabor>>, response: Response<List<Sabor>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var lista = mutableListOf<String>()
                        for( dark in it ){
                            lista.add( dark.nombre.toString() )
                        }
                        responseNombre.postValue( lista )
                    }
                }
            }
            override fun onFailure(call: Call<List<Sabor>>, t: Throwable) {
            }
        })
    }
}