package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Personalizacion
import retrofit2.Call
import retrofit2.http.*

interface PersonalizacionAPI {

    @POST( "personalizacion/registrar" )
    fun RegistrarPersonalizacion( @Body personalizacion: Personalizacion ): Call<Personalizacion>

    @PUT( "personalizacion/{id}" )
    fun UpdatePrecio( @Path( "id" ) id: Long, @Query( "precio" ) precio: Double ): Call<String>
}