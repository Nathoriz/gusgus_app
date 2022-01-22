package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Piso
import retrofit2.Call
import retrofit2.http.GET

interface PisoAPI {
    @GET( "piso/listar" )
    fun getPisos(): Call<List<Piso>>
}