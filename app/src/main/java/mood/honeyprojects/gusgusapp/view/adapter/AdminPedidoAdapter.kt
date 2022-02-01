package mood.honeyprojects.gusgusapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardOrdersBinding
import mood.honeyprojects.gusgusapp.model.entity.Pedido
import mood.honeyprojects.gusgusapp.view.ui.PedidoEstadoUpdateActivity

class AdminPedidoAdapter( private val pedidos: List<Pedido> ): RecyclerView.Adapter<AdminPedidoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return ViewHolder( layoutInflater.inflate( R.layout.item_card_orders, parent, false ) )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Pedido = pedidos[ position ]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return pedidos.size
    }
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardOrdersBinding.bind(itemView)

        fun bind(pedido: Pedido){
            binding.tvIdpedido.text = pedido.id.toString()
            binding.tvFechacreacionpedido.text = pedido.fechaCreacion
            binding.tvPreciototalpedido.text = pedido.monto.toString()
            binding.tvEstadopedido.text = pedido.estado?.nombre
            binding.ivImgpedido.setImageResource( R.drawable.ic_pedido )

            binding.root.setOnClickListener {
            val intent = Intent( binding.root.context, PedidoEstadoUpdateActivity::class.java )
            intent.putExtra( "id", pedido.id )
            binding.root.context.startActivity( intent )
            }
        }

    }
}