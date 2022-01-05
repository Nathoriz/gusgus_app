package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaUpdate
import retrofit2.Call
import retrofit2.http.*

interface NoticiaAPI {

    @GET( "noticias/listar" )
    fun listarNoticia(): Call<List<Noticias>>

    @POST( "noticias/guardar" )
    fun GuardarNoticia( @Body noticia: NoticiaResponse ): Call<Noticias>

    @PUT( "noticias/actualizar" )
    fun ActualizarNoticia( @Body noticia: NoticiaUpdate ): Call<String>

    @GET( "noticias/visibilidad" )
    fun FindNoticiasVisibilidad( @Query("aBoolean" ) aBoolean: Boolean ): Call<List<Noticias>>

    @GET( "noticias/filtro" )
    fun FiltroNoticia( @Query( "nombre" ) nombre: String ): Call<List<Noticias>>
}