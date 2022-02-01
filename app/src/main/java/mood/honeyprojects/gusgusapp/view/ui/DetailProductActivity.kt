package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityDetailProductBinding
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.model.entity.Relleno
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.DetalleProductoViewModel
class DetailProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailProductBinding
    private val detalleProducto: DetalleProductoViewModel by viewModels()
    private var valor = 1
    private var producto: Producto?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        GetDetalleProducto()
        Listener()
    }
    private fun Listener(){
        binding.aumentar.setOnClickListener { IncrementPrecio() }
        binding.disminuir.setOnClickListener { DecrementPrecio() }
        binding.btnRealizarpedido.setOnClickListener { Pedido() }
    }

    private fun GetDetalleProducto(){
        detalleProducto.DetalleProducto( Preferences.constantes.getIdProduct() )
    }
    private fun Pedido(){
        val intent = Intent( this, EntregaActivity::class.java )
        intent.putExtra( "idProduct", producto?.id )
        if( producto?.categoria?.nombre == "Tortas" ){
            intent.putExtra( "precio", "0.0" )
            intent.putExtra( "cantidad", "0" )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }else{
            intent.putExtra( "precio", binding.tvTotal.text.toString() )
            intent.putExtra( "cantidad", binding.tvCantidad.text.toString() )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
    }
    private fun InitViewModel(){
        detalleProducto.detalleProductoLiveData.observe( this,  Observer {
            if( it != null ){
                Picasso.get().load(it.detalle?.producto?.urlimg).into(binding.ivImgproduct)
                binding.tvNombreproducto.text = it.detalle?.producto?.nombre
                binding.tvDescripcion.text = it.detalle?.producto?.descripcion
                binding.tvTotal.text = "${it.detalle?.producto?.precio.toString()}"
                binding.precioFalso.text = "${it.detalle?.producto?.precio.toString()}"

                if (it.detalle?.diametros?.get(0)?.descripcion == "vacio") {
                    binding.rlDiametrocont.visibility = View.GONE
                }else{
                    binding.rlDiametrocont.visibility = View.VISIBLE
                }
                if (it.detalle?.altura?.descripcion == "vacio") {
                    binding.rlAlturacont.visibility = View.GONE
                }else{
                    binding.rlAlturacont.visibility = View.VISIBLE
                }
                if (it.detalle?.rellenos?.get(0)?.descripcion == "vacio") {
                    binding.rlRellenocont.visibility = View.GONE
                }else{
                    binding.rlRellenocont.visibility = View.VISIBLE
                }
                if( it.detalle?.sabores?.get(0)?.nombre == "vacio" ){
                    binding.rlSaborcont.visibility = View.GONE
                }else{
                    binding.rlSaborcont.visibility = View.VISIBLE
                }
                if (it.detalle?.cubierta?.nombre == "vacio") {
                    binding.rlCubiertacont.visibility = View.GONE
                }else{
                    binding.rlCubiertacont.visibility = View.VISIBLE
                }
                var diametros: String? = ""
                if (it.detalle?.diametros?.size == 1) {
                    diametros = it.detalle.diametros[0].descripcion
                } else {
                    for (diametro: Diametro in it.detalle?.diametros!!) {
                        diametros += diametro.descripcion + "\n"
                    }
                }
                binding.tvDiametro.text = diametros
                binding.tvAltura.text = it.detalle.altura?.descripcion

                if (it.detalle.sabores?.size == 1) {
                    val sabores = "#" + it.detalle.sabores[0].color
                    val color = Color.parseColor(sabores)
                    binding.ivSaborimg.setImageResource(R.drawable.ic_sabor)
                    binding.ivSaborimg.setColorFilter(color)
                    binding.tvSabor.text = it.detalle.sabores[0].nombre
                } else {
                    binding.ivSaborimg.setImageResource(R.drawable.ic_sabores)
                    var sabores: String? = ""
                    for (sabor: Sabor in it.detalle.sabores!!) {
                        sabores += sabor.nombre + "\n"
                    }
                    binding.tvSabor.text = sabores
                }
                if (it.detalle.rellenos?.size == 1) {
                    binding.tvRelleno.text = it.detalle.rellenos[0].descripcion
                } else {
                    var rellenos: String? = ""
                    for (relleno: Relleno in it.detalle.rellenos!!) {
                        rellenos += relleno.descripcion + "\n"
                    }
                    binding.tvRelleno.text = rellenos
                }

                binding.tvCubierta.text = it.detalle.cubierta?.nombre
                if (it.detalle.producto?.categoria?.nombre == "Tortas" || it.detalle.producto?.categoria?.nombre == "Packs") {
                    binding.rlCantidadcont.visibility = View.GONE
                } else {
                    binding.rlCantidadcont.visibility = View.VISIBLE
                }
                producto = it.detalle.producto
            }
        } )
    }
    private fun IncrementPrecio(){
        if( valor >= 1 ){
            binding.disminuir.visibility = View.VISIBLE
            binding.tvCantidad.text = (++valor).toString()

            val total = binding.precioFalso.text.toString()
            val superTotal = total.toDouble() * valor
            binding.tvTotal.text = superTotal.toString()
        }
    }
    private fun DecrementPrecio(){
        if( valor == 2 ){
            binding.disminuir.visibility = View.INVISIBLE
            binding.tvCantidad.text = (--valor).toString()
            val precio = binding.precioFalso.text.toString()
            binding.tvTotal.text = precio

        }else{
            binding.tvCantidad.text = (--valor).toString()

            val precio = binding.precioFalso.text.toString()
            val total = binding.tvTotal.text.toString()

            val superTotal = total.toDouble() - precio.toDouble()
            binding.tvTotal.text = superTotal.toString()
        }
    }
}