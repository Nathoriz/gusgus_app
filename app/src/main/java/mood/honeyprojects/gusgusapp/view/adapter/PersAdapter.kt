package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.listeners.PersListener
import mood.honeyprojects.gusgusapp.model.entity.Personalizacion

class PersAdapter( private val listPers: List<Personalizacion>, private val persPrecio: PersListener ): RecyclerView.Adapter<PersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return PersViewHolder( layoutInflater.inflate( R.layout.item_card_confirmarpedido, parent, false ) )
    }

    override fun onBindViewHolder(holder: PersViewHolder, position: Int) {
        val personalizacion: Personalizacion = listPers[ position ]
        holder.bind( personalizacion, persPrecio )
    }

    override fun getItemCount(): Int {
        return listPers.size
    }
}