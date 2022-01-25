package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.PersonalizacionPiso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PersonalizacionPisoAPI {
    @POST( "personalizacionpiso/registrar" )
    fun RegistrarPersonalizacionPiso( @Body personalizacion: PersonalizacionPiso ): Call<PersonalizacionPiso>

    @GET( "personalizacionpiso/cliente/{id}" )
    fun GetListByPersId( @Path( "id" ) id: Long ): Call<List<PersonalizacionPiso>>
}