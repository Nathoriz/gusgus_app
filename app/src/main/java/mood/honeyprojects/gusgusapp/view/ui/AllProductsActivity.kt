package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityAllProductsBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.model.entity.ProductoCubierta
import mood.honeyprojects.gusgusapp.view.adapter.AdminProductoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ProductoRellenoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ProductoSAdapter
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel

class AllProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllProductsBinding

    private val productoViewModel: ProductoViewModel by viewModels()
    private lateinit var adapter: AdminProductoAdapter
    private val listaProducto = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerViewProducto(binding.rvProductsoAllproducts)
        getListaProducto()
        FiltroProducto()
        listener()
    }

    private fun initViewModel() {
        productoViewModel.listaProductoLiveData.observe( this) {
            if (it != null) {
                binding.rvProductsoAllproducts.visibility = View.VISIBLE
//                binding.ivNotfoundSearch.visibility = View.INVISIBLE
                listaProducto.clear()
                listaProducto.addAll(it)
                adapter.notifyDataSetChanged()
            }
        }
        productoViewModel.responseMensaje.observe(  this) {
            if (it != null) {
                binding.rvProductsoAllproducts.visibility = View.INVISIBLE
//                binding.ivNotfoundSearch.visibility = View.VISIBLE
            }
        }
    }

    private fun FiltroProducto(){
        binding.svProductosAllproducts.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svProductosAllproducts.clearFocus()
                if (query != null) {
                    productoViewModel.filtroTodoProducto( query )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    productoViewModel.filtroTodoProducto( newText )
                }
                return false
            }
        } )
    }

    private fun fillRecyclerViewProducto(rv: RecyclerView) {
        adapter = AdminProductoAdapter(listaProducto)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = adapter
    }

    private fun getListaProducto() {
        productoViewModel.listarTodosProducto()
    }

    private fun listener() {
        binding.cvBackAllproduct.setOnClickListener {
            val intent = Intent( this, AdminMainActivity::class.java )
            startActivity( intent )
            finish()
        }
        binding.fbAddAllproducts.setOnClickListener {
            val intent = Intent( this, NewProductActivity::class.java )
            startActivity( intent )
            finish()
        }
    }


}