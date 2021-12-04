package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardProductoBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto

class ProductoViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding =ItemCardProductoBinding.bind( view )

    fun bind( producto: Producto ){
        binding.txtNombreTorta.text = producto.nombre
        Picasso.get().load( producto.urlimg ).into( binding.ivtorta )
    }
}