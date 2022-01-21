package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Detalle
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DetalleAPI {

    @POST( "detalles/registrar")
    fun RegistrarDetalle( @Body detalle: Detalle ): Call<Detalle>
}