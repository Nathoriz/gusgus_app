package mood.honeyprojects.gusgusapp.view.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.view.ui.EditNoticiaActivity

class NoticiaViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    val binding = ItemCardDesignBinding.bind( view )

    fun bind( noticia: Noticias ){
        Picasso.get().load( noticia.imgurl ).into( binding.ivImageDesign )
        binding.tvTextDesign.text = noticia.nombre
        binding.ivDetailDesign.setOnClickListener {
            val intent = Intent( binding.root.context, EditNoticiaActivity::class.java )
            intent.putExtra( "Noticia", noticia )
            binding.root.context.startActivity( intent )
        }
    }
}