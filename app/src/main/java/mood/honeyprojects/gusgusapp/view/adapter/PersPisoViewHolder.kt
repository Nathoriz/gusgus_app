package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ItemCardPisoBinding
import mood.honeyprojects.gusgusapp.model.entity.PersonalizacionPiso

class PersPisoViewHolder( view: View): RecyclerView.ViewHolder( view ) {
    val binding = ItemCardPisoBinding.bind( view )

    fun bind( persPiso: PersonalizacionPiso ){
        binding.spdiametropiso.setText( persPiso.diametro?.descripcion )
        binding.sprellenopiso.setText( persPiso.relleno?.descripcion )
        binding.spsaborpiso.setText( persPiso.sabor?.nombre )
    }
}