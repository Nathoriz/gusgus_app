package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.model.requestEntity.DetalleProductoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoAPI {

    @GET( "producto/listarAll" )
    fun ListProducto(): Call<List<Producto>>

    @GET( "producto/listar" )
    fun ListarProductPorCategoria( @Query("categoria") categoria: String ): Call<List<Producto>>

    @GET( "producto/filtro" )
    fun FiltroProducto( @Query("nombre") nombre: String ): Call<List<Producto>>

    @GET( "producto/{id}" )
    fun DetalleProducto( @Path("id") id: Long? ): Call<DetalleProductoResponse>
}