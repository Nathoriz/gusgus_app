package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.model.entity.Producto

class ProductoSAdapter( private val products: List<Producto> ): RecyclerView.Adapter<ProductoSViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoSViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return ProductoSViewHolder( layoutInflater.inflate( R.layout.item_card_producto, parent, false ) )
    }

    override fun onBindViewHolder(holder: ProductoSViewHolder, position: Int) {
        val item: Producto = products[ position ]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return products.size
    }
}