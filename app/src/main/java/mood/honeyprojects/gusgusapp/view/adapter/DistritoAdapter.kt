package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Distrito

class DistritoAdapter(private val distritos:List<Distrito>, private val itemClickListener:OnClickDistritoListener): RecyclerView.Adapter<DistritoAdapter.ViewHolder>() {
    interface OnClickDistritoListener{
        fun onDistritoClick(id: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistritoAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: DistritoAdapter.ViewHolder, position: Int) {
        val item:Distrito = distritos[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return distritos.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(distrito: Distrito){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = distrito.nombre
            binding.root.setOnClickListener{ distrito.id?.let { it1 ->
                itemClickListener.onDistritoClick(
                    it1
                )
            } }
        }
    }
}