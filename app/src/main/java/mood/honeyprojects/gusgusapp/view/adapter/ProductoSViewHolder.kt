package mood.honeyprojects.gusgusapp.view.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemProductoSearchBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.ui.DetailProductActivity
import java.lang.Exception

class ProductoSViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding = ItemProductoSearchBinding.bind( view )

    fun bind( producto: Producto ){
        binding.txtErrorS.visibility = View.GONE
        binding.txtDescripcion.text = producto.nombre
        Picasso.get().load( producto.urlimg ).into( binding.ivproducto, object: com.squareup.picasso.Callback {
            override fun onSuccess() {
                binding.progressBar2.visibility = View.GONE
                binding.txtErrorS.visibility = View.GONE
                binding.ivproducto.visibility = View.VISIBLE
                binding.txtDescripcion.visibility = View.VISIBLE
            }
            override fun onError(e: Exception?) {
                binding.progressBar2.visibility = View.GONE
                binding.txtErrorS.text = "No se puede cargar\nla imagen. \uD83D\uDE14"
                binding.txtErrorS.visibility = View.VISIBLE
                binding.ivproducto.visibility = View.INVISIBLE
                binding.txtDescripcion.visibility = View.VISIBLE
            }
        } )
        binding.root.setOnClickListener {
            val intent = Intent( binding.root.context, DetailProductActivity::class.java )
            Preferences.constantes.saveIdProduct( producto.id!! )
            binding.root.context.startActivity( intent )
        }
    }
}