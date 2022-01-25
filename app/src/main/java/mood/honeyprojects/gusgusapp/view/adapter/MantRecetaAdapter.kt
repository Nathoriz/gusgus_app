package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Receta

class MantRecetaAdapter(private val recetas:List<Receta>, private val itemClickListener: MantRecetaAdapter.OnClickRecetaListener): RecyclerView.Adapter<MantRecetaAdapter.ViewHolder>() {
    interface OnClickRecetaListener{
        fun onRecetaClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Receta = recetas[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return recetas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(receta: Receta, position: Int){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = receta.descripcion
            binding.root.setOnClickListener{ receta.id?.let { it1 ->
                itemClickListener.onRecetaClick(
                    it1,position
                )
            } }
        }
    }
}