package mood.honeyprojects.gusgusapp.view.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.view.ui.MantAlturaActivity

class AlturaViewHolder(view: View): RecyclerView.ViewHolder(view){
    val binding = ItemCardDesignBinding.bind(view)

    fun bind(altura:Altura){
        binding.cvContainerDesign.visibility = View.GONE
        binding.tvTextDesign.text = altura.descripcion
        binding.root.setOnClickListener{}

        binding.root.setOnClickListener{
            val intent = Intent(binding.root.context,MantAlturaActivity::class.java)
            intent.putExtra("id",altura.id)
            binding.root.context.startActivity(intent)
        }
    }
}