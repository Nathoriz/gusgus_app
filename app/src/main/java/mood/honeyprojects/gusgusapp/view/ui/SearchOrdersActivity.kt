package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivitySearchOrdersBinding
import mood.honeyprojects.gusgusapp.model.entity.Pedido
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.PedidoAdapter
import mood.honeyprojects.gusgusapp.viewModel.PedidoViewModel
import java.util.*
import kotlin.collections.ArrayList

class SearchOrdersActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchOrdersBinding
    private lateinit var adapter: PedidoAdapter
    private val pedidoViewModel: PedidoViewModel by viewModels()
    private val listaPedidos = mutableListOf<Pedido>()
    private var nombreEstado:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchOrdersBinding.inflate( layoutInflater )
        binding.noorder.visibility = View.GONE
        setContentView( binding.root )
        val intent = this.intent
        val extra = intent.extras
        val nombre = extra?.getString("key")
        nombreEstado = nombre

        supportActionBar?.hide()
        ListarByNombreAndId()
        PedidoViewModel()
        RecyclerView( binding.rvSearcordersSorder )
        SearchPedidos()
    }
    private fun SearchPedidos(){
        binding.svOrdersSorder.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svOrdersSorder.clearFocus()
                if( query != null ){
                    val idClient = Preferences.constantes.getIDCliente()
                    pedidoViewModel.FindPedidosClientsById( idClient, nombreEstado!!, query )
                }
               return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if( newText != null ){
                    val idClient = Preferences.constantes.getIDCliente()
                    pedidoViewModel.FindPedidosClientsById( idClient, nombreEstado!!, newText )
                }
                return false
            }
        })
    }
    private fun ListarByNombreAndId(){
        binding.tvStateorderSorder.text = nombreEstado
        pedidoViewModel.ListarByEstadoAndID( nombreEstado!!, Preferences.constantes.getIDCliente() )
    }
    private fun RecyclerView( rv: RecyclerView ){
        adapter = PedidoAdapter( listaPedidos )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }
    private fun PedidoViewModel(){
        pedidoViewModel.ListaPedidos.observe( this, Observer {
            if( it != null ){
                binding.noorder.visibility = View.GONE
                binding.rvSearcordersSorder.visibility = View.VISIBLE
                listaPedidos.clear()
                listaPedidos.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
        pedidoViewModel.responseMessage.observe( this, Observer {
            if( it != null ){
                binding.rvSearcordersSorder.visibility = View.INVISIBLE
                binding.noorder.visibility = View.VISIBLE
            }
        } )
    }
}