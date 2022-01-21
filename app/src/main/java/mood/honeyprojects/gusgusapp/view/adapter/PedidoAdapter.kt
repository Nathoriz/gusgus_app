package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardOrdersBinding
import mood.honeyprojects.gusgusapp.model.entity.Pedido

class PedidoAdapter( private val pedidos: List<Pedido> ): RecyclerView.Adapter<PedidoAdapter.viewHolder>() {

    class viewHolder( view: View ): RecyclerView.ViewHolder( view ) {
        val binding = ItemCardOrdersBinding.bind( view )
        fun bind( pedido: Pedido ){
            binding.tvIdpedido.text = pedido.id.toString()
            binding.tvFechacreacionpedido.text = pedido.fechaCreacion
            binding.tvPreciototalpedido.text = pedido.monto.toString()
            binding.tvEstadopedido.text = pedido.estado?.nombre
            if( pedido.estado?.nombre == "Enviado" ){ binding.ivImgpedido.setImageResource( R.drawable.ic_enviado ) }
            if( pedido.estado?.nombre == "En espera" ){ binding.ivImgpedido.setImageResource( R.drawable.ic_espera ) }
            if( pedido.estado?.nombre == "Pendiente" ){ binding.ivImgpedido.setImageResource( R.drawable.ic_pedido ) }
            if( pedido.estado?.nombre == "Cancelado" ){ binding.ivImgpedido.setImageResource( R.drawable.ic_cancelado ) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return viewHolder( layoutInflater.inflate( R.layout.item_card_orders, parent, false ) )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item: Pedido = pedidos[ position ]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }

}