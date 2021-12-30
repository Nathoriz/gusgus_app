package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Noticias

class NoticiaViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    val binding = ItemCardDesignBinding.bind( view )

    fun bind( noticia: Noticias ){
        Picasso.get().load( noticia.imgurl ).into( binding.ivImageDesign )
        binding.tvTextDesign.text = noticia.nombre
    }
}