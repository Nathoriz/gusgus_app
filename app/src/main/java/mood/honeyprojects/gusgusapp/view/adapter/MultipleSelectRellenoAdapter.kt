package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardRellenoBinding
import mood.honeyprojects.gusgusapp.model.entity.Relleno

class MultipleSelectRellenoAdapter (private val rellenos:List<Relleno>, private val itemClickListener: MultipleSelectRellenoAdapter.OnClickRellenoListener): RecyclerView.Adapter<MultipleSelectRellenoAdapter.ViewHolder>(){
    private val selectedItems = mutableListOf<String>()

    interface OnClickRellenoListener{
        fun onRellenoClick(nombre: String,position: Int,list: MutableList<String>)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleSelectRellenoAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_relleno,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MultipleSelectRellenoAdapter.ViewHolder, position: Int) {
        val item: Relleno = rellenos[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return rellenos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardRellenoBinding.bind(itemView)
        fun bind(relleno: Relleno, position: Int){
            binding.tvNombreItemrelleno.text = relleno.descripcion
            binding.root.setOnClickListener {
                if(!binding.tvNombreItemrelleno.isChecked){
                    binding.clContainerItemrelleno.setBackgroundResource(R.color.bk_item_color)
                    relleno.descripcion?.let { it1 -> selectedItems.add(it1) }
                    binding.tvNombreItemrelleno.isChecked = true
                }else{
                    binding.clContainerItemrelleno.setBackgroundResource(R.color.white)
                    relleno.descripcion?.let { it1 -> selectedItems.remove(it1) }
                    binding.tvNombreItemrelleno.isChecked = false
                }
                relleno.descripcion?.let { it1 ->
                    itemClickListener.onRellenoClick(
                        it1,position, selectedItems
                    )
                }
            }
        }
    }
}