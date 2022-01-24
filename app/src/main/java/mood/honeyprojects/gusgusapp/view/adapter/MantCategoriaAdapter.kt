package mood.honeyprojects.gusgusapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ItemCardDesignBinding
import mood.honeyprojects.gusgusapp.model.entity.Categoria

class MantCategoriaAdapter(private val categorias:List<Categoria>, private val itemClickListener: OnClickCategoriaListener) : RecyclerView.Adapter<MantCategoriaAdapter.ViewHolder>(){
    interface OnClickCategoriaListener{
        fun onCategoriaClick(id: Long,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_card_design,parent,false)
        return ViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:Categoria = categorias[position]
        holder.bind(item,position)
    }

    override fun getItemCount(): Int {
        return categorias.size
    }

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val binding = ItemCardDesignBinding.bind(itemView)

        fun bind(categoria: Categoria, position: Int){
            Picasso.get().load( categoria.urlimg ).into( binding.ivImageDesign )
            binding.tvTextDesign.text = categoria.nombre
            binding.root.setOnClickListener{
                categoria.id?.let { it1 -> itemClickListener.onCategoriaClick(it1,position) }
            } }
        }
}
