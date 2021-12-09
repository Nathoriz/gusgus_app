package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemProductoSearchBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto

class ProductoSViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding = ItemProductoSearchBinding.bind( view )

    fun bind( producto: Producto ){
        Picasso.get().load( producto.urlimg ).into( binding.ivproducto )
        binding.txtDescripcion.text = producto.descripcion
    }
}