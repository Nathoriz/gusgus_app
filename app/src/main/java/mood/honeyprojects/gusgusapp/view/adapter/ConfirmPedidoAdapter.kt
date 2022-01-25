package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.listeners.PedidoCategoNombre
import mood.honeyprojects.gusgusapp.listeners.ProductoDetailListener
import mood.honeyprojects.gusgusapp.model.entity.Producto

class ConfirmPedidoAdapter( private val productos: List<Producto>, private var productoDetail: ProductoDetailListener, private val precioTotal: Double, private val cantidad: Int, private val pedidoCate: PedidoCategoNombre):
        RecyclerView.Adapter<ConfirmPedidoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfirmPedidoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return ConfirmPedidoViewHolder( layoutInflater.inflate( R.layout.item_card_confirmarpedido, parent, false ) )
    }

    override fun onBindViewHolder(holder: ConfirmPedidoViewHolder, position: Int) {
        val item: Producto = productos[ position ]
        holder.bind( item, productoDetail, precioTotal, cantidad, pedidoCate )
    }

    override fun getItemCount(): Int {
        return productos.size
    }

}