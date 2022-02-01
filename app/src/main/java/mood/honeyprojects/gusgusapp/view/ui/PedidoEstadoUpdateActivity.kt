package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityPedidoEstadoUpdateBinding
import mood.honeyprojects.gusgusapp.model.entity.DetallePedido
import mood.honeyprojects.gusgusapp.model.entity.Insumo
import mood.honeyprojects.gusgusapp.model.entity.Pedido
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.view.adapter.AdminProductoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.DetallePedidoAdapter
import mood.honeyprojects.gusgusapp.viewModel.DetallePedidoViewModel
import mood.honeyprojects.gusgusapp.viewModel.PedidoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel

class PedidoEstadoUpdateActivity : AppCompatActivity() {
    private lateinit var binding:ActivityPedidoEstadoUpdateBinding

    private val pedidoViewModel: PedidoViewModel by viewModels()
    private var pedido: Pedido? = null
    private var IDpedido: Long = 0L

    private val detallePedidoViewModel: DetallePedidoViewModel by viewModels()
    private lateinit var detallePedidoAdapter: DetallePedidoAdapter
    private val listaDetallePedido = mutableListOf<DetallePedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoEstadoUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        IDpedido = intent.getLongExtra("id",0L)

        initViewModel()
        searchPedido()
        fillRecyclerViewDetallePedido(binding.rvDetallepedidoPedidoestadoupdate)
        getListaDetallePedido( )
        listener()
    }

    private fun initViewModel() {
        pedidoViewModel.responsePedidoLiveData.observe(this) {
            if (it != null) {
                pedido = it
                binding.tvIdPedidoestadoupdate.text = binding.tvIdPedidoestadoupdate.text.toString() + pedido?.id?.toString()
                binding.tvEstadoPedidoestadoupdate.text = pedido?.estado?.nombre
                binding.tvClientePedidoestadoupdate.text = "${pedido?.cliente?.nombre} ${pedido?.cliente?.apellido}"
                binding.tvFechaPedidoestadoupdate.text = pedido?.fechaCreacion
                binding.tvDistritoPedidoestadoupdate.text = pedido?.entrega?.distrito?.nombre
                binding.tvDireccionPedidoestadoupdate.text = pedido?.entrega?.direccion
                binding.tvTiempoPedidoestadoupdate.text = pedido?.entrega?.hora
                binding.tvTotalPedidoestadoupdate.text = pedido?.monto.toString()
                binding.tvEnvioPedidoestadoupdate.text = pedido?.entrega?.distrito?.precio.toString()
            }
        }

        pedidoViewModel.responseMessage.observe(this){
            if (it != null) {

            }
        }

        detallePedidoViewModel.responseDePedidoList.observe(this){
            if (it != null) {
                listaDetallePedido.clear()
                listaDetallePedido.addAll(it)
                detallePedidoAdapter.notifyDataSetChanged()
                for( dark in it ){
                    binding.tvSubtotalPedidoestadoupdate.text = dark.subtotal.toString()
                }
            }
        }
    }

    private fun fillRecyclerViewDetallePedido(rv: RecyclerView) {
        detallePedidoAdapter = DetallePedidoAdapter(listaDetallePedido)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = detallePedidoAdapter
    }

    private fun searchPedido() {
        pedidoViewModel.FindPedidoById(IDpedido)
    }

    private fun getListaDetallePedido(){
        detallePedidoViewModel.GetListByPedidoId( IDpedido )
    }

    private fun listener() {
        binding.cvBackPedidoestadoupdate.setOnClickListener {
            val intent = Intent( this, AllPedidosActivity::class.java )
            startActivity( intent )
            finish()
        }
        binding.btnEstadoPedidoestadoupdate.setOnClickListener {
            binding.lyEstadoPedidoestadoupdate.visibility = View.VISIBLE
        }

        binding.btnPendientePedidoestadoupdate.setOnClickListener {
            binding.lyEstadoPedidoestadoupdate.visibility = View.GONE
            binding.tvEstadoPedidoestadoupdate.text = "PENDIENTE"
            pedidoViewModel.cambioEstado(IDpedido,"PENDIENTE")
        }
        binding.btnCanceladoPedidoestadoupdate.setOnClickListener {
            binding.lyEstadoPedidoestadoupdate.visibility = View.GONE
            binding.tvEstadoPedidoestadoupdate.text = "CANCELADO"
            pedidoViewModel.cambioEstado(IDpedido,"CANCELADO")
        }
        binding.btnEnviadoPedidoestadoupdate.setOnClickListener {
            binding.lyEstadoPedidoestadoupdate.visibility = View.GONE
            binding.tvEstadoPedidoestadoupdate.text = "ENVIADO"
            pedidoViewModel.cambioEstado(IDpedido,"ENVIADO")
        }
    }


}