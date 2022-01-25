package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityConfirmOrderBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityDetallePedidoBinding
import mood.honeyprojects.gusgusapp.model.entity.DetallePedido
import mood.honeyprojects.gusgusapp.view.adapter.DetallePedidoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.PersAdapter
import mood.honeyprojects.gusgusapp.viewModel.DetallePedidoViewModel
import mood.honeyprojects.gusgusapp.viewModel.PedidoViewModel

class DetallePedidoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePedidoBinding
    private lateinit var adapter: DetallePedidoAdapter
    private val pedidoViewModel: PedidoViewModel by viewModels()
    private val detaPedidoViewModel: DetallePedidoViewModel by viewModels()
    private var idPedido: Long?= 0L

    private val lista = mutableListOf<DetallePedido>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("keyIdPedido")
        idPedido = id
        toast( idPedido.toString() )

        GetPedido( idPedido!! )
        ViewModelPedido()
        ListDetallePedido( idPedido!! )
        ViewModelDetallePedido()
        RecyclerViewDetaPedido( binding.rvDetallepedidoDetallepedido )
    }

    private fun GetMonth( month: Int ): String{
        val monthNames = arrayOf(
            "Ene",
            "Febre",
            "Marz",
            "Abri",
            "May",
            "Juni",
            "Juli",
            "Agost",
            "Septi",
            "Octub",
            "Noviem",
            "Diciem"
        )
        return monthNames[ month - 1 ]
    }
    private fun ListDetallePedido( id: Long ){
        detaPedidoViewModel.GetListByPedidoId( id )
    }
    private fun GetPedido( id: Long ){
        pedidoViewModel.FindPedidoById( id )
    }

    private fun RecyclerViewDetaPedido( rv: RecyclerView ) {
        adapter = DetallePedidoAdapter( lista )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }

    private fun ViewModelDetallePedido(){
        detaPedidoViewModel.responseDePedidoList.observe( this, Observer {
            if( it != null ){
                lista.clear()
                lista.addAll( it )
                adapter.notifyDataSetChanged()
                for( dark in it ){
                    binding.tvSubtotalDetallepedido.text = dark.subtotal.toString()
                }

            }
        } )
    }
    private fun ViewModelPedido(){
        pedidoViewModel.responsePedidoLiveData.observe( this, Observer {
            if( it != null ){
                val datosMes = it.entrega?.fecha.toString()
                val mes = datosMes.substring( 0, 6 )
                val year = datosMes.substring( 0, 4 )
                val MES = mes.substring( mes.length - 1 ).toInt()
                val dia = datosMes.substring( datosMes.length - 2 )

                binding.tvIdDetallepedido.text = it.id.toString()
                binding.txtestado.text = it.estado?.nombre
                binding.tvFechaDetallepedido.text = "$year ${ GetMonth( MES ) } $dia"
                binding.tvDireccionDetallepedido.text = it.entrega?.direccion
                binding.tvDistritoDetallepedido.text = it.entrega?.distrito?.nombre
                binding.tvHoraDetallepedido.text = it.entrega?.hora


                binding.tvTotalDetallepedido.text = it.monto.toString()
                if( it.entrega?.envio == true ){
                    binding.tvEnvioDetallepedido.text = it.entrega.distrito?.precio.toString()
                }else{
                    binding.tvEnvioDetallepedido.text = "0.0"
                }

            }
        } )
    }
    private fun toast( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}