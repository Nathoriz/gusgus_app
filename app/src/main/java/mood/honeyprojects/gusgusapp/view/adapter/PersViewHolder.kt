package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardConfirmarpedidoBinding
import mood.honeyprojects.gusgusapp.listeners.PersListener
import mood.honeyprojects.gusgusapp.model.entity.Personalizacion

class PersViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    val binding = ItemCardConfirmarpedidoBinding.bind( view )

    fun bind( pers: Personalizacion, persPrecio: PersListener ){
        Picasso.get().load( pers.urlimg ).into( binding.ivImgorder )
        binding.tvNombreorderproducto.text = pers.nombre
        binding.tvPrecio.text = pers.precio.toString()
        binding.tvCategoria.visibility = View.GONE
        binding.tvCantorder.text = "1"
        binding.ivPluscant.visibility = View.INVISIBLE
        persPrecio.GetPrecioPers( pers.precio!! )
    }
}