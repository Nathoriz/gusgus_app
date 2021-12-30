package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.model.entity.Noticias

class NoticiaAdapter( private val noticias: List<Noticias> ): RecyclerView.Adapter<NoticiaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return NoticiaViewHolder( layoutInflater.inflate( R.layout.item_card_design, parent, false ) )
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val item: Noticias = noticias[ position ]
        holder.bind( item )
    }

    override fun getItemCount(): Int {
        return noticias.size
    }
}