package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Sabor
import retrofit2.Call

import retrofit2.http.*

interface SaborAPI {
    @GET("sabor/listar")
    fun listarSabor(): Call<List<Sabor>>

    @POST("sabor/guardar")
    fun guardarSabor(@Body sabor: Sabor): Call<Sabor>

    @PUT("sabor/actualizar")
    fun actualizarSabor(@Body sabor: Sabor): Call<String>

    @GET("sabor/{id}")
    fun buscarSabor(@Path("id") id: Long): Call<Sabor>

    @DELETE("sabor/eliminar/{id}")
    fun eliminarSabor(@Path("id") id: Long): Call<String>
}