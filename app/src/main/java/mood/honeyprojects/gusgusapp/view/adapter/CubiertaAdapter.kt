package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Cubierta

class CubiertaAdapter(private val cubiertas:List<Cubierta>,private val itemClickListener:OnClickCubiertaListener): RecyclerView.Adapter<CubiertaAdapter.ViewHolder>(){

    interface OnClickCubiertaListener{
        fun onCubiertaClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:Cubierta = cubiertas[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return cubiertas.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(cubierta: Cubierta,position: Int){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = cubierta.nombre
            binding.root.setOnClickListener{ cubierta.id?.let { it1 ->
                itemClickListener.onCubiertaClick(
                    it1,position
                )
            } }
        }
    }
}