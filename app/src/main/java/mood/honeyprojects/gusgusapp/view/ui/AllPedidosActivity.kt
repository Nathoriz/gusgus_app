package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityAllPedidosBinding
import mood.honeyprojects.gusgusapp.model.entity.Pedido
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.view.adapter.AdminPedidoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.AdminProductoAdapter
import mood.honeyprojects.gusgusapp.viewModel.PedidoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel

class AllPedidosActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAllPedidosBinding

    private val pedidoViewModel: PedidoViewModel by viewModels()
    private lateinit var adapter: AdminPedidoAdapter
    private val listaPedido = mutableListOf<Pedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerViewPedido(binding.rvPedidosAllpredidos)
        getListaPedido()
        FiltroPedido()
        listener()
    }

    private fun initViewModel() {
        pedidoViewModel.ListaPedidos.observe( this) {
            if (it != null) {
                binding.rvPedidosAllpredidos.visibility = View.VISIBLE
//                binding.ivNotfoundSearch.visibility = View.INVISIBLE
                listaPedido.clear()
                listaPedido.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        pedidoViewModel.responseMessage.observe(  this) {
            if (it != null) {
                binding.rvPedidosAllpredidos.visibility = View.INVISIBLE
//                binding.ivNotfoundSearch.visibility = View.VISIBLE
            }
        }
    }

    private fun fillRecyclerViewPedido(rv: RecyclerView) {
        adapter = AdminPedidoAdapter(listaPedido)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
    }

    private fun getListaPedido() {
        pedidoViewModel.listarPedidos()
    }

    private fun FiltroPedido() {
        binding.svPedidosAllpredidos.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svPedidosAllpredidos.clearFocus()
                if (query != null) {
                    pedidoViewModel.FindPedidosById( query )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    pedidoViewModel.FindPedidosById( newText )
                }
                return false
            }
        } )
    }

    private fun listener() {
        binding.cvBackAllpredidos.setOnClickListener {
            val intent = Intent( this, AdminMainActivity::class.java )
            startActivity( intent )
            finish()
        }
    }
}