package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Personalizacion
import retrofit2.Call
import retrofit2.http.*

interface PersonalizacionAPI {

    @POST( "personalizacion/registrar" )
    fun RegistrarPersonalizacion( @Body personalizacion: Personalizacion ): Call<Personalizacion>

    @PUT( "personalizacion/{id}" )
    fun UpdatePrecio( @Path( "id" ) id: Long, @Query( "precio" ) precio: Double ): Call<String>

    @GET( "personalizacion/cliente/{id}" )
    fun GetAllMyCakePers( @Path( "id" ) id: Long ): Call<List<Personalizacion>>

    @GET( "personalizacion/list/{id}" )
    fun findByIdClientAndNombreTorta(
        @Path( "id" ) id: Long,
        @Query( "nombre" ) nombre: String ): Call<List<Personalizacion>>

    @GET( "personalizacion/{id}" )
    fun GetById( @Path( "id" ) id: Long ): Call<Personalizacion>
}