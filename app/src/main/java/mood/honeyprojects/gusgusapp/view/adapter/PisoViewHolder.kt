package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ItemRadiobuttonOpcionBinding
import mood.honeyprojects.gusgusapp.listeners.PisoListener
import mood.honeyprojects.gusgusapp.model.entity.Piso

class PisoViewHolder( view: View): RecyclerView.ViewHolder( view ) {
    val binding = ItemRadiobuttonOpcionBinding.bind( view )

    fun bind( piso: Piso, pisoListener: PisoListener ){
        binding.rbPisoOpcion.text = piso.nombre
        binding.rbPisoOpcion.setOnClickListener { pisoListener.ListenerPiso( piso ) }

    }
}