package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Altura

class AlturaAdapter (private val alturas:List<Altura>, private val itemClickListener:OnClickListener): RecyclerView.Adapter<AlturaAdapter.ViewHolder>(){

    interface OnClickListener{
        fun onAlturaClick(id: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:Altura = alturas[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return alturas.size
    }

    inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(altura:Altura){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = altura.descripcion
            binding.root.setOnClickListener{ altura.id?.let { it1 ->
                itemClickListener.onAlturaClick(
                    it1
                )
            } }
        }
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlturaViewHolder {
//        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
//        return AlturaViewHolder(layoutInflater.inflate(R.layout.item_card_design, parent, false))
//    }
//
//    override fun onBindViewHolder(holder: AlturaViewHolder, position: Int) {
//        val item:Altura = alturas[position]
//        holder.bind(item)
//    }
//
//    override fun getItemCount(): Int {
//        return alturas.size
//    }
}