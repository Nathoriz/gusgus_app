package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Receta

class SelectRecetaAdapter (private val recetas:List<Receta>, private val itemClickListener: SelectRecetaAdapter.OnClickRecetaListener): RecyclerView.Adapter<SelectRecetaAdapter.ViewHolder>() {

    interface OnClickRecetaListener{
        fun onRecetaClick(receta: Receta)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRecetaAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: SelectRecetaAdapter.ViewHolder, position: Int) {
        val item: Receta = recetas[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return recetas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)
        fun bind(receta: Receta){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = receta.descripcion.toString()
            binding.root.setOnClickListener {
                itemClickListener.onRecetaClick(receta)
            }
        }
    }
}