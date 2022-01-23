package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Diametro
import retrofit2.Call
import retrofit2.http.GET

interface DiametroAPI {

    @GET( "diametro/listar" )
    fun GetAll(): Call<List<Diametro>>
}