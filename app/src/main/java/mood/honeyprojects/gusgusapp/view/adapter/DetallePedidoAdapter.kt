package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.model.entity.DetallePedido

class DetallePedidoAdapter( private val listaDetaPedido: List<DetallePedido> ): RecyclerView.Adapter<DetallePedidoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetallePedidoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return DetallePedidoViewHolder( layoutInflater.inflate( R.layout.item_card_confirmarpedido, parent, false ) )
    }

    override fun onBindViewHolder(holder: DetallePedidoViewHolder, position: Int) {
        val detaPedido: DetallePedido = listaDetaPedido[ position ]
        holder.bind( detaPedido )
    }

    override fun getItemCount(): Int {
        return listaDetaPedido.size
    }

}