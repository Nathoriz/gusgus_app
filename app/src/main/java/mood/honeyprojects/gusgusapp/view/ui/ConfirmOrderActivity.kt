package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityConfirmOrderBinding
import mood.honeyprojects.gusgusapp.listeners.ProductoDetailListener
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.view.adapter.ConfirmPedidoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ConfirmPedidoViewHolder
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel

class ConfirmOrderActivity : AppCompatActivity(), ProductoDetailListener {
    private lateinit var binding: ActivityConfirmOrderBinding
    private lateinit var adapter: ConfirmPedidoAdapter

    private val productoviewModel: ProductoViewModel by viewModels()

    private val productos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        FindProductForId()
        InitRecyclerView( binding.rvOrderproduct )
    }
    private fun FindProductForId(){
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("idProduct")
        productoviewModel.ListForIdProduct( id!! )
    }
    private fun InitRecyclerView( rv: RecyclerView ){
        val intent = this.intent
        val extra = intent.extras
        val precioTotal = extra?.getString("precio")
        val cantidad = extra?.getString("cantidad")

        adapter = ConfirmPedidoAdapter( productos, this , precioTotal?.toDouble()!!, cantidad?.toInt()!! )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }
    private fun InitViewModel(){
        productoviewModel.listaProductoLiveData.observe( this, Observer {
            if( it != null ){
                productos.clear()
                productos.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
    }

    override fun ProductoDetail(precioTotal: Double) {
        binding.tvSubtotal.text = precioTotal.toString()
    }
}