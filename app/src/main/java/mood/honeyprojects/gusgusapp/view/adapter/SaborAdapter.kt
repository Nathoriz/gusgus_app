package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Sabor

class SaborAdapter(private val sabores:List<Sabor>, private  val itemClickListener:OnClickSaborListener) : RecyclerView.Adapter<SaborAdapter.ViewHolder>(){
    interface OnClickSaborListener{
        fun onSaborClick(id: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:Sabor = sabores[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return sabores.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(sabor: Sabor){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = sabor.nombre
            binding.root.setOnClickListener{ sabor.id?.let { it1 ->
                itemClickListener.onSaborClick(
                    it1
                )
            } }
        }
    }
}