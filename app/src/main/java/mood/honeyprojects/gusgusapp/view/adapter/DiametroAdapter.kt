package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding

import mood.honeyprojects.gusgusapp.model.entity.Diametro

class DiametroAdapter(private val diametros:List<Diametro>, private val itemClickListener:OnClickDiametroListener):RecyclerView.Adapter<DiametroAdapter.ViewHolder>() {

    interface OnClickDiametroListener{
        fun onDiametroClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Diametro = diametros[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return diametros.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(diametro: Diametro,position: Int){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = diametro.descripcion
            binding.root.setOnClickListener{
                diametro.id?.let { it1 ->
                    itemClickListener.onDiametroClick(
                        it1,position
                    )
                }
            } }
        }
}