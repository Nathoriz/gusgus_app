package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.serviceAPI.CategoriaAPI
import mood.honeyprojects.gusgusapp.model.serviceAPI.ProductoAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriaViewModel: ViewModel() {

    val listaCategoriaLiveData = MutableLiveData<List<Categoria>>()
    val responseMsCategoria = MutableLiveData<String>()

    fun ListarCategoria(){
        val response = RetrofitHelper.getRetrofit().create( CategoriaAPI::class.java ).listarCategoria()
        response.enqueue( object: Callback<List<Categoria>>{
            override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        listaCategoriaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        responseMsCategoria.postValue( it.string() )
                    }
                }
            }

            override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
            }
        } )
    }
}