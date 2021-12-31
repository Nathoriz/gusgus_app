package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VisibilidadAPI {

    @GET( "visibilidad/buscar" )
    fun getVisibilidad( @Query( "aBoolean" ) aBoolean: Boolean ): Call<Visibilidad>
}