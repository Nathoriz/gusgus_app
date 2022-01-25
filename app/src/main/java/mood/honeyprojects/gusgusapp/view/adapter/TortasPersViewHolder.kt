package mood.honeyprojects.gusgusapp.view.adapter

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ItemTortasPersonalizadasBinding
import mood.honeyprojects.gusgusapp.model.entity.Personalizacion
import mood.honeyprojects.gusgusapp.view.ui.DetallePersonalizacionActivity

class TortasPersViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
    val binding = ItemTortasPersonalizadasBinding.bind( view )

    fun bind( tortasPers: Personalizacion ){
        Picasso.get().load( tortasPers.urlimg ).into( binding.imageView6 )
        binding.tvNomtortaperso.text = tortasPers.nombre
        binding.tvPreciotortaperso.text = tortasPers.precio.toString()
        binding.tvVer.setOnClickListener { Go( tortasPers ) }
    }
    private fun Go( pers: Personalizacion ){
        val id = pers.id
        val intent = Intent( binding.root.context, DetallePersonalizacionActivity::class.java )
        intent.putExtra( "keyIdPers", id )
        binding.root.context.startActivity( intent )
    }
}