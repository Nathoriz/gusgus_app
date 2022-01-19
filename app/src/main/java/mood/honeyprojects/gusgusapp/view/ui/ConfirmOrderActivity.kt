package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.classes.DatePickerFragment
import mood.honeyprojects.gusgusapp.classes.TimePickerFragment
import mood.honeyprojects.gusgusapp.databinding.ActivityConfirmOrderBinding
import mood.honeyprojects.gusgusapp.listeners.ProductoDetailListener
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.ConfirmPedidoAdapter
import mood.honeyprojects.gusgusapp.viewModel.*
import java.text.SimpleDateFormat
import java.util.*

class ConfirmOrderActivity : AppCompatActivity(), ProductoDetailListener {
    private lateinit var binding: ActivityConfirmOrderBinding
    private lateinit var adapter: ConfirmPedidoAdapter
    private val detalleViewModel: DetallePedidoViewModel by viewModels()
    private val productoviewModel: ProductoViewModel by viewModels()
    private val entregaViewModel: EntregaViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()

    private val productos = mutableListOf<Producto>()
    private var distrito: Distrito?=null //NO esta en uso Para registrar el distrito a futuro ( No olvidarse )
    private var nombreDistri: String?=null
    private var entregaid: Long?=null
    private var pedidoid: Long?=null
    private var cantidad: Int?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        FindEntrega()
        ViewModelDetallePedido()
        FindProductForId()
        ViewModelEntrega()
        ViewModelProducto()
        ViewModelPedido()
        InitRecyclerView( binding.rvOrderproduct )
        Listener()
    }
    private fun Listener(){
        binding.imageButton.setOnClickListener {
            val intent = Intent( this, ClientMainActivity::class.java )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
        binding.btnConfirmPedido.setOnClickListener {
            RegistrarPedido()
        }
    }
    private fun RegistrarDetaPedido(){
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("idProduct")
        val pedido = Pedido( pedidoid, null, null, null, null, null )
        val producto = Producto( id, null, null, null, null, null, null, null )
        val personalizacion = Personalizacion( 0, null, null, null, null, null, null )
        val detallePedi = DetallePedido( 0, pedido, producto, personalizacion, cantidad, 0.0 )
        detalleViewModel.RegistrarDetallePedido( detallePedi )
    }
    private fun RegistrarPedido(){
        val cliente = Cliente( Preferences.constantes.getIDCliente(), null, null, null )
        val entrega = Entrega( entregaid, null, null, null, null, null )
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val currentDate = sdf.format(Date())
        val estado = Estado( 3L, null )
        val monto: Double = binding.tvConfirmorderTotal.text.toString().toDouble()
        val pedido = Pedido( null, cliente, entrega, currentDate.toString(), estado, monto )
        pedidoViewModel.RegistrarPedido( pedido )
    }
    private fun FindProductForId(){
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("idProduct")
        productoviewModel.ListForIdProduct( id!! )
    }
    private fun FindEntrega(){
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("id")
        //entregaViewModel.FindEntregaById( Preferences.constantes.getIdEntrega() + 1L )
        entregaViewModel.FindEntregaById( id!! )
        //entregaViewModel.FindEntregaById(  1L )
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
    private fun ViewModelDetallePedido(){
        detalleViewModel.responseDePedido.observe( this, Observer {
            if( it != null ){
                val intent = Intent( this, PasarelaActivity::class.java )
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
                startActivity( intent )
            }
        } )
    }
    private fun ViewModelPedido(){
        pedidoViewModel.responseMessage.observe( this, Observer {
            if( it != null ) {
                toast(it)
            }
        } )
        pedidoViewModel.responsePedidoLiveData.observe( this, Observer {
            if( it != null ){
                pedidoid = it.id
                RegistrarDetaPedido()
            }
        } )
    }
    private fun ViewModelProducto(){
        productoviewModel.listaProductoLiveData.observe( this, Observer {
            if( it != null ){
                productos.clear()
                productos.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
    }
    fun isNumeric(valor: String): Boolean {
        try {
            val d = valor.toDouble()
        } catch (e: NumberFormatException) {
            return false
        }
        return true
    }
    private fun ViewModelEntrega(){
        entregaViewModel.responseLiveData.observe( this, Observer {
            if( it != null ){
                entregaid = it.id
                val datosMes = it.fecha.toString()
                val mes = datosMes.substring( 0, 6 )
                val year = datosMes.substring( 0, 4 )
                val MES = mes.substring( mes.length - 1 ).toInt()
                val dia = datosMes.substring( datosMes.length - 2 )

                binding.tvDireccionConforder.text = "${ it.direccion.toString() } ${ it.distrito?.nombre.toString() }"
                binding.tvFechaConforder.text = "$year ${ GetMonth( MES ) } $dia"
                binding.tvTiempoConforder.text = it.hora.toString()
                if( it.envio == false ){
                    binding.tvEnvio.text = "0.0"
                }else{
                    binding.tvEnvio.text = it.distrito?.precio.toString()
                }
            }
        } )
    }
    override fun ProductoDetail(precioTotal: Double, cant:Int) {
        binding.tvSubtotal.text = precioTotal.toString()
        //var envio = binding.tvEnvio.text.toString()
        cantidad = cant
        var envio = 0.0
        envio = if (isNumeric(binding.tvEnvio.text.toString())) binding.tvEnvio.text.toString().toDouble() else 0.0
        val total = precioTotal + envio
        binding.tvConfirmorderTotal.text = total.toString()
    }
    private fun toast( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}