package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardCategoriaBinding
import mood.honeyprojects.gusgusapp.listeners.CategoriaListener
import mood.honeyprojects.gusgusapp.model.entity.Categoria

class CategoriaViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding = ItemCardCategoriaBinding.bind( view )

    fun bind( categoria: Categoria, cateListener: CategoriaListener ){
        Picasso.get().load( categoria.urlimg ).into( binding.ivCategoria )
        binding.root.setOnClickListener { cateListener.categoriaClicked( categoria ) }
    }
}