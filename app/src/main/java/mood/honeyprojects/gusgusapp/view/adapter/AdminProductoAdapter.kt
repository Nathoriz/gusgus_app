package mood.honeyprojects.gusgusapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardProductoAdminBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.view.ui.UpdateDeleteProductActivity

class AdminProductoAdapter( private val products: List<Producto> ): RecyclerView.Adapter<AdminProductoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return ViewHolder( layoutInflater.inflate( R.layout.item_card_producto_admin, parent, false ) )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Producto = products[ position ]
        holder.bind( item,position )
    }

    override fun getItemCount(): Int {
        return products.size
    }
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardProductoAdminBinding.bind(itemView)

        fun bind(producto: Producto, position: Int){
            Picasso.get().load( producto.urlimg ).into( binding.ivImgItemproductoadmin)
            binding.tvNombreItempructoadmin.text = producto.nombre.toString()
            binding.tvPrecioItempructoadmin.text = producto.precio.toString()
            binding.root.setOnClickListener{
                val intent = Intent( binding.root.context, UpdateDeleteProductActivity::class.java )
                intent.putExtra("id",producto.id)
                binding.root.context.startActivity( intent )
             }
        }
    }
}