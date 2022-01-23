package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.listeners.PisoListener
import mood.honeyprojects.gusgusapp.model.entity.Piso

class PisoAdapter( private val pisos: List<Piso>, private val pisolistener: PisoListener ): RecyclerView.Adapter<PisoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PisoViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from( parent.context )
        return PisoViewHolder( layoutInflater.inflate( R.layout.item_radiobutton_opcion, parent, false ) )
    }

    override fun onBindViewHolder(holder: PisoViewHolder, position: Int) {
        val item: Piso = pisos[ position ]
        holder.bind( item, pisolistener )
    }

    override fun getItemCount(): Int {
        return pisos.size
    }
}