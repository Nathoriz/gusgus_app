package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaUpdate
import mood.honeyprojects.gusgusapp.model.serviceAPI.NoticiaAPI
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticiaViewModel: ViewModel() {

    val noticiasLiveData = MutableLiveData<List<Noticias>>()
    val noticiaLiveData = MutableLiveData<Noticias>()
    val messageResponse = MutableLiveData<String>()
    val urlNoticiasLiveData = MutableLiveData<List<String>>()

    fun ListarNoticias(){
        val response = RetrofitHelper.getRetrofit().create( NoticiaAPI::class.java ).listarNoticia()
        response.enqueue( object: Callback<List<Noticias>> {
            override fun onResponse(call: Call<List<Noticias>>, response: Response<List<Noticias>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        noticiasLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<List<Noticias>>, t: Throwable) {

            }
        })
    }
    fun GuardarNoticia( noticia: NoticiaResponse ){
        val response = RetrofitHelper.getRetrofit().create( NoticiaAPI::class.java ).GuardarNoticia( noticia )
        response.enqueue( object: Callback<Noticias> {
            override fun onResponse(call: Call<Noticias>, response: Response<Noticias>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        noticiaLiveData.postValue( it )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageResponse.postValue( getErrorMessage( it.string() ) )
                    }
                }
            }

            override fun onFailure(call: Call<Noticias>, t: Throwable) {

            }
        })
    }
    fun ActualizarNoticia( noticia: NoticiaUpdate ){
        val response = RetrofitHelper.getRetrofit().create( NoticiaAPI::class.java ).ActualizarNoticia( noticia )
        response.enqueue( object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        messageResponse.postValue( it )
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }
    fun FindNoticiasbyVisibilidad( boolean: Boolean ){
        val response = RetrofitHelper.getRetrofit().create( NoticiaAPI::class.java ).FindNoticiasVisibilidad( boolean )
        response.enqueue( object: Callback<List<Noticias>> {
            override fun onResponse(call: Call<List<Noticias>>, response: Response<List<Noticias>>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        var list = mutableListOf<String>()
                        for( url in it ){
                            list.add( url.imgurl.toString() )
                        }
                        urlNoticiasLiveData.postValue( list )
                    }
                }
            }

            override fun onFailure(call: Call<List<Noticias>>, t: Throwable) {
            }
        })
    }
    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}