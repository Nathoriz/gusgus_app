package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface NoticiaAPI {

    @GET( "noticias/listar" )
    fun listarNoticia(): Call<List<Noticias>>

    @POST( "noticias/guardar" )
    fun GuardarNoticia( @Body noticia: NoticiaResponse ): Call<Noticias>
}