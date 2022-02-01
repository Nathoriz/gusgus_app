package mood.honeyprojects.gusgusapp.model.serviceAPI

import mood.honeyprojects.gusgusapp.model.entity.ProductoDiametro
import retrofit2.Call
import retrofit2.http.*

interface ProductoDiametroAPI {
    @GET( "productodiametro/listar/{productoid}" )
    fun listarProductoDiametro(@Path("productoid" ) id: Long): Call<List<ProductoDiametro>>

    @POST( "productodiametro/guardar" )
    fun guardarProductoDiametro( @Body productoDiametro: ProductoDiametro): Call<ProductoDiametro>

    @PUT( "productodiametro/actualizar" )
    fun actualizarProductoDiametro( @Body productoDiametro: ProductoDiametro): Call<String>

    @GET( "productodiametro/{id}" )
    fun buscarProductoDiametro( @Path("id" ) id: Long ): Call<ProductoDiametro>

    @DELETE( "productodiametro/eliminar/{id}" )
    fun eliminarProductoDiametro( @Path( "id" ) id: Long ): Call<String>
}