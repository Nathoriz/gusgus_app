package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Categoria
import retrofit2.Call
import retrofit2.http.GET

interface CategoriaAPI {

    @GET( "categoria/listar" )
    fun listarCategoria(): Call<List<Categoria>>
}