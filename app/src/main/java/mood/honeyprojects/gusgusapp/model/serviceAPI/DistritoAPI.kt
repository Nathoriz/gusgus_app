package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Distrito
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DistritoAPI {

    @GET( "distrito/listar" )
    fun listarDistrito(): Call<List<Distrito>>

    @GET( "/distrito/buscar" )
    fun BuscarDistrito( @Query( "nombre" ) nombre: String ): Call<Distrito>
}