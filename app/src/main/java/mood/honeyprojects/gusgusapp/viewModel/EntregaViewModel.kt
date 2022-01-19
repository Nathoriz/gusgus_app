package mood.honeyprojects.gusgusapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mood.honeyprojects.gusgusapp.core.RetrofitHelper
import mood.honeyprojects.gusgusapp.listeners.EntregaListener
import mood.honeyprojects.gusgusapp.model.entity.Entrega
import mood.honeyprojects.gusgusapp.model.serviceAPI.EntregaAPI
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EntregaViewModel: ViewModel() {
    val responseLiveData = MutableLiveData<Entrega>()
    val messageLiveData = MutableLiveData<String>()

    fun Guardar( entrega: Entrega, entregaListener: EntregaListener ){
        val response = RetrofitHelper.getRetrofit().create( EntregaAPI::class.java ).GuardarEntrega( entrega )
        response.enqueue( object: Callback<Entrega> {
            override fun onResponse(call: Call<Entrega>, response: Response<Entrega>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        entregaListener.idEntregaListener( it.id!! )
                        messageLiveData.postValue( "Datos registrados." )
                    }
                }
                response.errorBody()?.let {
                    if( response.code() == 400 ){
                        messageLiveData.postValue( getErrorMessage(it.string()) )
                    }
                }
            }

            override fun onFailure(call: Call<Entrega>, t: Throwable) {

            }
        })
    }
    fun FindEntregaById( id: Long ){
        val response = RetrofitHelper.getRetrofit().create( EntregaAPI::class.java ).FindEntregaById( id )
        response.enqueue( object: Callback<Entrega> {
            override fun onResponse(call: Call<Entrega>, response: Response<Entrega>) {
                response.body()?.let {
                    if( response.code() == 200 ){
                        responseLiveData.postValue( it )
                    }
                }
            }

            override fun onFailure(call: Call<Entrega>, t: Throwable) {
            }
        })
    }
    fun getErrorMessage(raw: String): String{
        val objects = JSONObject(raw)
        return objects.getString("message")
    }
}