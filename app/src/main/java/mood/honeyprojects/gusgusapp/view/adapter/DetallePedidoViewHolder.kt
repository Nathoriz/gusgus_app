package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardConfirmarpedidoBinding
import mood.honeyprojects.gusgusapp.model.entity.DetallePedido

class DetallePedidoViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding = ItemCardConfirmarpedidoBinding.bind( view )

    fun bind( detaPedido: DetallePedido ){
        if( detaPedido.producto != null ){
            Picasso.get().load( detaPedido.producto.urlimg ).into( binding.ivImgorder )
            binding.tvNombreorderproducto.text = detaPedido.producto.nombre
            binding.tvPrecio.text = "S/ ${ detaPedido.producto.precio }"
            binding.tvCategoria.text = detaPedido.producto.categoria?.nombre
            binding.ivPluscant.visibility = View.INVISIBLE
            binding.tvCantorder.text = detaPedido.cantidad.toString()
        }
        if( detaPedido.personalizacion != null ){
            Picasso.get().load( detaPedido.personalizacion.urlimg ).into( binding.ivImgorder )
            binding.tvNombreorderproducto.text = detaPedido.personalizacion.nombre
            binding.tvPrecio.text = "S/ ${ detaPedido.personalizacion.precio }"
            binding.tvCategoria.visibility = View.GONE
            binding.ivPluscant.visibility = View.INVISIBLE
            binding.tvCantorder.text = detaPedido.cantidad.toString()
        }
    }
}