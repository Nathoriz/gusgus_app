package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.serviceAPI.DiametroAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiametroViewModel: ViewModel() {
    val responseDescripcion = MutableLiveData<List<String>>()

    fun GetAllDiametro(){
        val response = RetrofitHelper.getRetrofit().create( DiametroAPI::class.java ).listarDiametro()
        response.enqueue( object: Callback<List<Diametro>> {
            override fun onResponse(call: Call<List<Diametro>>, response: Response<List<Diametro>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var lista = mutableListOf<String>()
                        for( dark in it ){
                            lista.add( dark.descripcion.toString() )
                        }
                        responseDescripcion.postValue( lista )
                    }
                }
            }
            override fun onFailure(call: Call<List<Diametro>>, t: Throwable) {
            }
        })
    }
}