package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Relleno

class RellenoAdapter(private val rellenos:List<Relleno>,private val itemClickListener: OnClickRellenoListener) : RecyclerView.Adapter<RellenoAdapter.ViewHolder>(){
    interface OnClickRellenoListener{
        fun onRellenoClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Relleno = rellenos[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return rellenos.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(relleno:Relleno,position: Int){
            binding.cvContainerDesign.visibility = View.GONE
            binding.tvTextDesign.text = relleno.descripcion
            binding.root.setOnClickListener{ relleno.id?.let { it1 ->
                itemClickListener.onRellenoClick(
                    it1,position
                )
            } }
        }
    }
}