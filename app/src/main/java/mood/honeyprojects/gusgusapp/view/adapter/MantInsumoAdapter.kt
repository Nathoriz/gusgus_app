package mood.honeyprojects.gusgusapp.view.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Insumo

class MantInsumoAdapter(private val insumos:List<Insumo>, private val itemClickListener: OnClickInsumoListener) : RecyclerView.Adapter<MantInsumoAdapter.ViewHolder>(){
    interface OnClickInsumoListener{
        fun onInsumoClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: Insumo = insumos[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return insumos.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(insumo: Insumo, position: Int){
            binding.cvContainerDesign.setBackgroundColor(Color.parseColor("#00ffffff"))
            Picasso.get().load( insumo.img ).into( binding.ivImageDesign )
            binding.tvTextDesign.text = insumo.nombre
            binding.root.setOnClickListener{ insumo.id?.let { it1 ->
                itemClickListener.onInsumoClick(
                    it1,position
                )
            } }
        }
    }
}