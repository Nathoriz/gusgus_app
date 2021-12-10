package mood.honeyprojects.gusgusapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemCardProductoBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto
import java.lang.Exception

class ProductoViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    private val binding =ItemCardProductoBinding.bind( view )

    fun bind( producto: Producto ){
        binding.txterror.visibility = View.GONE
        binding.txtNombreTorta.text = producto.nombre
        Picasso.get().load( producto.urlimg ).into( binding.ivtorta, object: com.squareup.picasso.Callback{
            override fun onSuccess() {
                binding.progressBar.visibility = View.GONE
                binding.txterror.visibility = View.GONE
                binding.ivtorta.visibility = View.VISIBLE
                binding.txtNombreTorta.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                binding.progressBar.visibility = View.GONE
                binding.txterror.text = "No se puede cargar\nla imagen. \uD83D\uDE14"
                binding.txterror.visibility = View.VISIBLE
                binding.ivtorta.visibility = View.INVISIBLE
                binding.txtNombreTorta.visibility = View.VISIBLE
            }
        } )
    }
}