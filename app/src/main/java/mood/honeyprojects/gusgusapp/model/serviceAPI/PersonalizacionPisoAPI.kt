package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.PersonalizacionPiso
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PersonalizacionPisoAPI {
    @POST( "personalizacionpiso/registrar" )
    fun RegistrarPersonalizacionPiso( @Body personalizacion: PersonalizacionPiso ): Call<PersonalizacionPiso>
}