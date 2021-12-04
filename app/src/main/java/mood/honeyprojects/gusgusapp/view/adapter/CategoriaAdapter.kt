package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.listeners.CategoriaListener
import mood.honeyprojects.gusgusapp.model.entity.Categoria

class CategoriaAdapter( private val categorias: List<Categoria>, private val categoriaListener: CategoriaListener ): RecyclerView.Adapter<CategoriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return CategoriaViewHolder( layoutInflater.inflate( R.layout.item_card_categoria, parent, false ) )
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val item: Categoria = categorias[ position ]
        holder.bind( item, categoriaListener )
    }

    override fun getItemCount(): Int {
        return categorias.size
    }
}