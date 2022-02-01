package mood.honeyprojects.gusgusapp.view.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDiametroBinding
import mood.honeyprojects.gusgusapp.model.entity.Diametro

class MultipleSelectDiametroAdapter (private val diametros:List<Diametro>, private val itemClickListener: MultipleSelectDiametroAdapter.OnClickDiametroListener): RecyclerView.Adapter<MultipleSelectDiametroAdapter.ViewHolder>() {
    private val selectedItems = mutableListOf<String>()

    interface OnClickDiametroListener{
        fun onDiametroClick(nombre: String,position: Int,list: MutableList<String>)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleSelectDiametroAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_diametro,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: MultipleSelectDiametroAdapter.ViewHolder, position: Int) {
        val item: Diametro = diametros[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return diametros.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDiametroBinding.bind(itemView)
        fun bind(diametro: Diametro, position: Int){
            binding.tvNombreItemdiametro.text = diametro.descripcion
            binding.root.setOnClickListener {
                if(!binding.tvNombreItemdiametro.isChecked){
                    binding.clContainerItemdiametro.setBackgroundResource(R.color.bk_item_color)
                    diametro.descripcion?.let { it1 -> selectedItems.add(it1) }
                    binding.tvNombreItemdiametro.isChecked = true
                }else{
                    binding.clContainerItemdiametro.setBackgroundResource(R.color.white)
                    diametro.descripcion?.let { it1 -> selectedItems.remove(it1) }
                    binding.tvNombreItemdiametro.isChecked = false
                }
                diametro.descripcion?.let { it1 ->
                    itemClickListener.onDiametroClick(
                        it1,position, selectedItems
                    )
                }
            }
        }
    }
}