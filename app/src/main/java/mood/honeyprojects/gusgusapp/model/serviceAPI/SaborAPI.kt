package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Sabor
import retrofit2.Call
import retrofit2.http.GET

interface SaborAPI {

    @GET( "sabor/listar" )
    fun GetAll(): Call<List<Sabor>>
}