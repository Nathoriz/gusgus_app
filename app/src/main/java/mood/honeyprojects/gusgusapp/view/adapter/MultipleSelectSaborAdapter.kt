package mood.honeyprojects.gusgusapp.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardSaborBinding
import mood.honeyprojects.gusgusapp.model.entity.Sabor

class MultipleSelectSaborAdapter (private val sabores:List<Sabor>, private val itemClickListener: MultipleSelectSaborAdapter.OnClickSaborListener): RecyclerView.Adapter<MultipleSelectSaborAdapter.ViewHolder>() {
    private val selectedItems = mutableListOf<String>()

    interface OnClickSaborListener{
        fun onSaborClick(nombre: String,position: Int,list: MutableList<String>)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleSelectSaborAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_sabor,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MultipleSelectSaborAdapter.ViewHolder, position: Int) {
        val item: Sabor = sabores[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return sabores.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardSaborBinding.bind(itemView)
        fun bind(sabor: Sabor, position: Int){
            val color = "#" + sabor.color
            val colorSabor = Color.parseColor(color)
            binding.ivColorItemsabor.setColorFilter(colorSabor)
            binding.tvNombreItemsabor.text = sabor.nombre
            binding.root.setOnClickListener {
                if(!binding.tvNombreItemsabor.isChecked){
                    binding.clContainerItemsabor.setBackgroundResource(R.color.bk_item_color)
                    sabor.nombre?.let { it1 -> selectedItems.add(it1) }
                    binding.tvNombreItemsabor.isChecked = true
                }else{
                    binding.clContainerItemsabor.setBackgroundResource(R.color.white)
                    sabor.nombre?.let { it1 -> selectedItems.remove(it1) }
                    binding.tvNombreItemsabor.isChecked = false
                }
                sabor.nombre?.let { it1 ->
                    itemClickListener.onSaborClick(
                        it1,position, selectedItems
                    )
                }
            }
        }
    }
}