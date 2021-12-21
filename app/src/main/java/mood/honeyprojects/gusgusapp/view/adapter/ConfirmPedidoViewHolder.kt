package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardConfirmarpedidoBinding
import mood.honeyprojects.gusgusapp.listeners.ProductoDetailListener
import mood.honeyprojects.gusgusapp.model.entity.Producto

class ConfirmPedidoViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding = ItemCardConfirmarpedidoBinding.bind( view )
    private var valor = 1

    fun bind( producto: Producto, productoDeail: ProductoDetailListener, precioTotal: Double, cantidad: Int ){
        Picasso.get().load( producto.urlimg ).into( binding.ivImgorder )
        binding.tvNombreorderproducto.text = producto.nombre
        ValiCantidad( cantidad )
        ValiPrecio( precioTotal, producto.precio!! )
        ValiCategoria( producto.categoria?.nombre, productoDeail )
        binding.tvCategoria.text = producto.categoria?.nombre

        binding.ivPluscant.setOnClickListener {
            Incrementar( productoDeail )
            if( binding.tvCantorder.text == "1" ){
                productoDeail.ProductoDetail( producto.precio )
            }else{
                productoDeail.ProductoDetail( binding.tvPrecio.text.toString().toDouble() )
            }
        }
        binding.ivMinuscant.setOnClickListener {
            Decrementar( productoDeail )
            if( binding.tvCantorder.text == "1" ){
                productoDeail.ProductoDetail( producto.precio )
            }else{
                productoDeail.ProductoDetail( binding.tvPrecio.text.toString().toDouble() )
            }
        }

    }
    private fun ValiCategoria( categoria: String?, productoDeail: ProductoDetailListener ){
        if( categoria == "Tortas" ){
            binding.ivPluscant.visibility = View.INVISIBLE
            productoDeail.ProductoDetail( binding.tvPrecio.text.toString().toDouble() )
        }else{
            binding.ivPluscant.visibility = View.VISIBLE
            binding.ivMinuscant.visibility = View.INVISIBLE
            productoDeail.ProductoDetail( binding.tvPrecio.text.toString().toDouble() )
        }
    }
    private fun ValiPrecio( precioOther: Double, precioNow: Double ){
        if( precioOther == 0.0 ){
            binding.tvPrecio.text = precioNow.toString()
            binding.txtprecioFalso.text = precioNow.toString()
        }else{
            binding.tvPrecio.text = precioOther.toString()
            binding.txtprecioFalso.text = precioNow.toString()
        }
    }
    private fun ValiCantidad( cantidad: Int ){
        if( cantidad == 0 ){
            valor = 1
            binding.tvCantorder.text = valor.toString()
        }else{
            valor = cantidad
            binding.tvCantorder.text = cantidad.toString()
            binding.ivMinuscant.visibility = View.VISIBLE
        }
    }
    private fun Incrementar( productoDeail: ProductoDetailListener ){
        if( valor >= 1 ){
            binding.ivMinuscant.visibility = View.VISIBLE
            binding.tvCantorder.text = (++valor).toString()

            val total = binding.txtprecioFalso.text.toString()
            val superTotal = total.toDouble() * valor
            binding.tvPrecio.text = superTotal.toString()

            productoDeail.ProductoDetail( superTotal )
        }
    }
    private fun Decrementar( productoDeail: ProductoDetailListener ){
        if( valor == 2 ){
            binding.ivMinuscant.visibility = View.INVISIBLE
            binding.tvCantorder.text = (--valor).toString()
            val precio = binding.txtprecioFalso.text.toString()
            binding.tvPrecio.text = precio

        }else{
            binding.tvCantorder.text = (--valor).toString()

            val precio = binding.txtprecioFalso.text.toString()
            val total = binding.tvPrecio.text.toString()

            val superTotal = total.toDouble() - precio.toDouble()
            binding.tvPrecio.text = superTotal.toString()

            productoDeail.ProductoDetail( superTotal )
        }
    }
}