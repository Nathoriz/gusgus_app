package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.model.entity.PersonalizacionPiso

class PersPisoAdapter( private val persPisos: List<PersonalizacionPiso> ): RecyclerView.Adapter<PersPisoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersPisoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return PersPisoViewHolder( layoutInflater.inflate( R.layout.item_card_piso, parent, false ) )
    }

    override fun onBindViewHolder(holder: PersPisoViewHolder, position: Int) {
        val persPiso: PersonalizacionPiso = persPisos[ position ]
        holder.bind( persPiso )
    }

    override fun getItemCount(): Int {
        return persPisos.size
    }
}