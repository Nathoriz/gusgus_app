package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.model.entity.Producto

class ProductoAdapter( private val productos: List<Producto> ): RecyclerView.Adapter<ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return ProductoViewHolder( layoutInflater.inflate( R.layout.item_card_producto, parent, false ) )
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item: Producto = productos[ position ]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}