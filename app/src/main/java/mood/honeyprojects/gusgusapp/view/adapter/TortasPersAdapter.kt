package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.model.entity.Personalizacion

class TortasPersAdapter( private val tortas: List<Personalizacion> ): RecyclerView.Adapter<TortasPersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TortasPersViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return TortasPersViewHolder( layoutInflater.inflate( R.layout.item_tortas_personalizadas, parent, false ) )
    }

    override fun onBindViewHolder(holder: TortasPersViewHolder, position: Int) {
        val torta: Personalizacion = tortas[ position ]
        holder.bind( torta )
    }

    override fun getItemCount(): Int {
        return tortas.size
    }
}