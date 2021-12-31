package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaUpdate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface NoticiaAPI {

    @GET( "noticias/listar" )
    fun listarNoticia(): Call<List<Noticias>>

    @POST( "noticias/guardar" )
    fun GuardarNoticia( @Body noticia: NoticiaResponse ): Call<Noticias>

    @PUT( "noticias/actualizar" )
    fun ActualizarNoticia( @Body noticia: NoticiaUpdate ): Call<String>
}