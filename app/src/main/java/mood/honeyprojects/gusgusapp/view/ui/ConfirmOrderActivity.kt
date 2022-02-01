package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_modal_detalle.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.classes.DatePickerFragment
import mood.honeyprojects.gusgusapp.classes.TimePickerFragment
import mood.honeyprojects.gusgusapp.databinding.ActivityConfirmOrderBinding
import mood.honeyprojects.gusgusapp.listeners.PedidoCategoNombre
import mood.honeyprojects.gusgusapp.listeners.PersListener
import mood.honeyprojects.gusgusapp.listeners.ProductoDetailListener
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.ConfirmPedidoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.PersAdapter
import mood.honeyprojects.gusgusapp.viewModel.*
import java.text.SimpleDateFormat
import java.util.*

class ConfirmOrderActivity : AppCompatActivity(), ProductoDetailListener, PersListener, PedidoCategoNombre {
    private lateinit var binding: ActivityConfirmOrderBinding
    private lateinit var adapter: ConfirmPedidoAdapter
    private lateinit var adapterPers: PersAdapter
    private val detalleViewModel: DetallePedidoViewModel by viewModels()
    private val productoviewModel: ProductoViewModel by viewModels()
    private val entregaViewModel: EntregaViewModel by viewModels()
    private val pedidoViewModel: PedidoViewModel by viewModels()
    private val detalleCumpleViewModel: DetalleViewModel by viewModels()
    private val personalizacionViewModel: PersonalizacionViewModel by viewModels()

    private val productos = mutableListOf<Producto>()
    private val listPers = mutableListOf<Personalizacion>()
    private var precioTotal: Double?=null
    private var nombreCatego: String?=null
    private var nombreCliente: String?=null
    private var entregaid: Long?=null
    private var pedidoid: Long?=null
    private var cantidad: Int?=null
    private var ok: Boolean?=null
    private var idPerso: Long?= 0L
    private var precienvi: Double =0.0
    private var precioPer: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("keyidPersonali")
        idPerso = id
        if( idPerso != 0L ){
            toast( idPerso.toString() )
            binding.rvPersonalizacionConfirmorder.visibility = View.VISIBLE
            binding.rvOrderproduct.visibility = View.GONE
            GetPersTorta( idPerso!! )
            ViewModelPersonalizacion()
            RecyclerViewPersonalizacion( binding.rvPersonalizacionConfirmorder )

        }else{
            FindProductForId()
            InitRecyclerView( binding.rvOrderproduct )
            ViewModelProducto()
        }
        FindEntrega()
        ViewModelDetallePedido()

        ViewModelDetalleCumple()
        ViewModelEntrega()

        ViewModelPedido()

        Listener()

    }
    private fun Listener(){
        binding.imageButton.setOnClickListener {
            val intent = Intent( this, ClientMainActivity::class.java )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
        binding.btnConfirmPedido.setOnClickListener {
            lifecycleScope.launch {
                binding.progressBar2.visibility = View.VISIBLE
                binding.btnConfirmPedido.visibility = View.INVISIBLE
                val response = async( Dispatchers.IO ){ RegistrarPedido() }
                if( response.await() ){
                    binding.progressBar2.visibility = View.GONE
                    binding.btnConfirmPedido.visibility = View.VISIBLE
                    BuildModalDeatalle()
                }
            }
        }
    }
    private fun BuildModalDeatalle(){
        val smallbinding = layoutInflater.inflate( R.layout.layout_modal_detalle, null )
        val builder = AlertDialog.Builder( this@ConfirmOrderActivity )
        builder.setView( smallbinding )

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource( android.R.color.transparent )
        dialog.setCancelable( false )
        var frase = "vacio"
        if( nombreCatego == "Muñecos" ){
            smallbinding.et_frase_detalle.visibility = View.GONE
        }

        smallbinding.btn_enviar_detalle.setOnClickListener {
            if( nombreCatego != "Muñecos" ){
                frase = smallbinding.et_frase_detalle.text.toString()
            }
            val pedido = Pedido( pedidoid, null, null, null, null, null )
            val detalle = Detalle(
                0,
                frase,
                smallbinding.et_observacion_detalle.text.toString(),
                pedido )
            detalleCumpleViewModel.RegistrarDetalle( detalle )
            //dialog.dismiss()
            val intent = Intent( this, PasarelaActivity::class.java )
            intent.putExtra( "keyprecio", precioTotal )
            intent.putExtra( "keynombre", nombreCliente )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
    }

    /*
    * Personalizacion
    */
    private fun GetPersTorta( id: Long ){
        personalizacionViewModel.GetById( id )
    }
    private fun RegistrarDetaPedido(){
        val intent = this.intent
        val extra = intent.extras
        val id = extra?.getLong("idProduct")
        val pedido = Pedido( pedidoid, null, null, null, null, null )
        val producto = Producto( id, null, null, null, null, null, null, null )
        val personalizacion = Personalizacion( 0, null, null, null, null, null )
        val detallePedi = DetallePedido( 0, pedido, producto, personalizacion, cantidad, 0.0 )
        if( idPerso != 0L ){
            val product = Producto( 0, null, null, null, null, null, null, null )
            val pers = Personalizacion( idPerso, null, null, null, null, null )
            val detaPedi = DetallePedido( 0, pedido, product, pers, 1, 0.0 )
            detalleViewModel.RegistrarDetallePedido( detaPedi )
        }else{
            detalleViewModel.RegistrarDetallePedido( detallePedi )
        }
    }
    private fun RegistrarPedido(): Boolean {
        Thread.sleep( 2000 )
        val cliente = Cliente( Preferences.constantes.getIDCliente(), null, null, null )
        val entrega = Entrega( entregaid, null, null, null, null, null )
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val currentDate = sdf.format(Date())
        val estado = Estado( 1L, null )
        val monto: Double = binding.tvConfirmorderTotal.text.toString().toDouble()
        precioTotal = monto
        val pedido = Pedido( null, cliente, entrega, currentDate.toString(), estado, monto )
        pedidoViewModel.RegistrarPedido( pedido )
        return true
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
    /*
    * Personalizacion
    */
    private fun RecyclerViewPersonalizacion( rv: RecyclerView ){
        adapterPers = PersAdapter( listPers, this )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapterPers
    }

    private fun InitRecyclerView( rv: RecyclerView ){
        val intent = this.intent
        val extra = intent.extras
        val precioTotal = extra?.getString("precio")
        val cantidad = extra?.getString("cantidad")

        adapter = ConfirmPedidoAdapter( productos, this , precioTotal?.toDouble()!!, cantidad?.toInt()!!, this )
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

    /*
        * Personalizacion
        */
    private fun ViewModelPersonalizacion(){
        personalizacionViewModel.responsePersonalizacion.observe( this, Observer {
            if( it != null ){
                listPers.clear()
                listPers.add( it )
                adapterPers.notifyDataSetChanged()
            }
        } )
    }
    private fun ViewModelDetalleCumple(){
        detalleCumpleViewModel.MessageDetalle.observe( this,  Observer {
            if( it != null ){
                toast( it )
            }
        } )
    }
    private fun ViewModelDetallePedido(){
        detalleViewModel.responseDePedido.observe( this, Observer {
            if( it != null ){
                ok = true
                nombreCliente = "${it.pedido?.cliente?.nombre} ${it.pedido?.cliente?.apellido}"
                //nombreCatego = it.producto?.categoria?.nombre
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
                    if( idPerso != 0L ){
                        binding.tvConfirmorderTotal.text = precioPer.toString()
                    }
                }else{
                    binding.tvEnvio.text = it.distrito?.precio.toString()
                    if( idPerso != 0L ){
                        precienvi = it.distrito?.precio?.toDouble()!!
                        val total = precioPer + precienvi
                        binding.tvConfirmorderTotal.text = total.toString()
                    }
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

    override fun GetPrecioPers(precio: Double) {
        binding.tvSubtotal.text = precio.toString()
        precioPer = precio
    }

    override fun NombreListener(nombre: String) {
        nombreCatego = nombre
    }
}