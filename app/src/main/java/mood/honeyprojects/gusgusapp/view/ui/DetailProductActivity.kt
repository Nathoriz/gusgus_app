package mood.honeyprojects.gusgusapp.view.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityDetailProductBinding
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.entity.Relleno
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.DetalleProductoViewModel

class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val detalleProducto: DetalleProductoViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        GetDetalleProducto()
    }
    private fun GetDetalleProducto(){
        detalleProducto.DetalleProducto( Preferences.constantes.getIdProduct() )
    }
    private fun InitViewModel(){
        detalleProducto.detalleProductoLiveData.observe( this,  Observer {
            if( it != null ){
                Picasso.get().load( it.detalle?.producto?.urlimg ).into( binding.ivImgproduct )
                binding.tvNombreproducto.text = it.detalle?.producto?.nombre
                binding.tvDescripcion.text = it.detalle?.producto?.descripcion
                binding.tvTotal.text = "S/ ${it.detalle?.producto?.precio.toString()}"
                if( it.detalle?.diametros?.get(0)?.descripcion == "vacio" ){
                    binding.rlDiametrocont.visibility = View.GONE
                }
                if( it.detalle?.altura?.descripcion == "vacio" ){
                    binding.rlAlturacont.visibility = View.GONE
                }
                if( it.detalle?.rellenos?.get(0)?.descripcion == "vacio" ){
                    binding.rlRellenocont.visibility = View.GONE
                }
                var diametros: String?=""
                    if( it.detalle?.diametros?.size == 1 ){
                        diametros = it.detalle.diametros[0].descripcion
                    }else{
                        for( diametro: Diametro in it.detalle?.diametros!! ){
                            diametros += diametro.descripcion + "\n"
                        }
                    }
                binding.tvDiametro.text = diametros
                binding.tvAltura.text = it.detalle.altura?.descripcion

                if( it.detalle.sabores?.size == 1 ){
                    val sabores = "#"+it.detalle.sabores[0].color
                    val color = Color.parseColor( sabores )
                    binding.ivSaborimg.setImageResource( R.drawable.ic_sabor )
                    binding.ivSaborimg.setColorFilter( color )
                    binding.tvSabor.text = it.detalle.sabores[0].nombre
                }else{
                    binding.ivSaborimg.setImageResource( R.drawable.ic_sabores )
                    var sabores: String?=""
                    for( sabor: Sabor in it.detalle.sabores!! ){
                        sabores += sabor.nombre + "\n"
                    }
                    binding.tvSabor.text = sabores
                }
                if( it.detalle.rellenos?.size == 1 ){
                    binding.tvRelleno.text = it.detalle.rellenos[0].descripcion
                }else{
                    var rellenos: String?=""
                    for( relleno: Relleno in it.detalle.rellenos!! ){
                        rellenos += relleno.descripcion + "\n"
                    }
                    binding.tvRelleno.text = rellenos
                }

                binding.tvCubierta.text = it.detalle.cubierta?.nombre
                if( it.detalle.producto?.categoria?.nombre == "Tortas" || it.detalle.producto?.categoria?.nombre == "Packs" ){
                    binding.rlCantidadcont.visibility = View.GONE
                }else{
                    binding.rlCantidadcont.visibility = View.VISIBLE
                }
            }
        } )
    }
}