package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Personalizacion
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PersonalizacionAPI {

    @POST( "personalizacion/registrar" )
    fun RegistrarPersonalizacion( @Body personalizacion: Personalizacion ): Call<Personalizacion>

}