package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.FragmentOrdersBinding
import mood.honeyprojects.gusgusapp.model.entity.Pedido
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.PedidoAdapter
import mood.honeyprojects.gusgusapp.viewModel.PedidoViewModel

class OrdersFragment : Fragment() {
    private lateinit var binding: FragmentOrdersBinding
    private lateinit var adapter: PedidoAdapter
    private val pedidoViewModel: PedidoViewModel by viewModels()

    private val pedidos = mutableListOf<Pedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrdersBinding.inflate( inflater, container, false )

        ListarPedido()
        PedidoViewModel()
        Listener()
        RecyclerViewPedido( binding.rvOrders )
        return binding.root
    }

    private fun Listener(){
        binding.ivEspera.setOnClickListener { Go( "En espera" ) }
        binding.ivPendiente.setOnClickListener { Go( "Pendiente" ) }
        binding.ivEnviado.setOnClickListener { Go( "Enviado" ) }
        binding.ivCancelado.setOnClickListener { Go( "Cancelado" ) }
    }
    private fun Go( nombre: String ){
        val intent = Intent( context, SearchOrdersActivity::class.java )
        intent.putExtra( "key", nombre )
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
        startActivity( intent )
    }
    private fun RecyclerViewPedido( rv: RecyclerView ){
        adapter = PedidoAdapter( pedidos )
        rv.layoutManager = LinearLayoutManager( context )
        rv.adapter = adapter
    }
    private fun ListarPedido(){
        pedidoViewModel.ListarPedidoIDClient( Preferences.constantes.getIDCliente() )
    }
    private fun PedidoViewModel(){
        pedidoViewModel.ListaPedidos.observe( viewLifecycleOwner, Observer {
            if( it != null ){
                pedidos.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
    }
}