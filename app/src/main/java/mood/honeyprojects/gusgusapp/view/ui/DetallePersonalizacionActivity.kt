package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityDetallePersonalizacionBinding
import mood.honeyprojects.gusgusapp.model.entity.PersonalizacionPiso
import mood.honeyprojects.gusgusapp.view.adapter.PersPisoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ProductoAdapter
import mood.honeyprojects.gusgusapp.viewModel.PersonalizacionPisoViewModel
import mood.honeyprojects.gusgusapp.viewModel.PersonalizacionViewModel

class DetallePersonalizacionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePersonalizacionBinding
    private lateinit var adapter: PersPisoAdapter
    private val personalizacionPisoViewModel: PersonalizacionPisoViewModel by viewModels()
    private val personalizacionViewModel: PersonalizacionViewModel by viewModels()

    private var idPers: Long? = 0L

    private val lista = mutableListOf<PersonalizacionPiso>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePersonalizacionBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("keyIdPers")
        idPers = id

        GetPersonalizacion( idPers!! )
        ViewModelPersonalizacion()
        GetListPersPiso( idPers!! )
        ViewModelPersPiso()
        RecyclerView( binding.rvPisosDetallepersonalizacion )
        Listener()
    }

    private fun Listener(){
        binding.ivHideDetallepersonalizadas.setOnClickListener {
            binding.rvPisosDetallepersonalizacion.visibility = View.GONE
            binding.ivHideDetallepersonalizadas.visibility = View.GONE
            binding.ivShowDetallepersonalizacion.visibility = View.VISIBLE
        }
        binding.ivShowDetallepersonalizacion.setOnClickListener {
            binding.rvPisosDetallepersonalizacion.visibility = View.VISIBLE
            binding.ivShowDetallepersonalizacion.visibility = View.GONE
            binding.ivHideDetallepersonalizadas.visibility = View.VISIBLE
        }
        binding.ivCloseDetallepersonalizacion.setOnClickListener {
            val intent = Intent( this, TortasPersonalizadasActivity::class.java )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
        binding.btnPedidoDetallepersonalizacion.setOnClickListener {
            val intent = Intent( this, EntregaActivity::class.java )
            intent.putExtra( "keyIdP", idPers )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
    }

    private fun GetListPersPiso( id: Long ){
        personalizacionPisoViewModel.ListByPersId( id )
    }
    private fun GetPersonalizacion( id: Long ){
        personalizacionViewModel.GetById( id );
    }
    private fun RecyclerView( rv: RecyclerView ){
        adapter = PersPisoAdapter( lista )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }

    private fun ViewModelPersPiso(){
        personalizacionPisoViewModel.responseDataList.observe( this, Observer {
            if( it != null ){
                lista.clear()
                lista.addAll( it )
                adapter.notifyDataSetChanged()
                binding.rvCantpisosDetallepersonalizacion.text = "${it.size} Pisos"
            }
        } )
    }
    private fun ViewModelPersonalizacion(){
        personalizacionViewModel.responsePersonalizacion.observe( this, Observer {
            if( it != null ){
                binding.tvNombreDetallepersonalizacion7.text = it.nombre
                Picasso.get().load( it.urlimg ).into( binding.ivImgDetallepersonalizacion )
                binding.tvPrecioDetallepersonalizacion.text = "S/${ it.precio.toString() }"
                binding.tvCubiertaDetallepersonalizacion1.text = it.cubierta?.nombre
            }
        } )
    }
    private fun toast( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}