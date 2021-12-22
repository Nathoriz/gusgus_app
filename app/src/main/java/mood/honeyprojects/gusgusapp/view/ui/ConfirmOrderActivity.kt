package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.classes.DatePickerFragment
import mood.honeyprojects.gusgusapp.databinding.ActivityConfirmOrderBinding
import mood.honeyprojects.gusgusapp.listeners.ProductoDetailListener
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.view.adapter.ConfirmPedidoAdapter
import mood.honeyprojects.gusgusapp.viewModel.DistritoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel

class ConfirmOrderActivity : AppCompatActivity(), ProductoDetailListener {
    private lateinit var binding: ActivityConfirmOrderBinding
    private lateinit var adapter: ConfirmPedidoAdapter
    private val distritoViewModel: DistritoViewModel by viewModels()
    private val productoviewModel: ProductoViewModel by viewModels()

    private val productos = mutableListOf<Producto>()
    private var distrito: Distrito?=null //NO esta en uso Para registrar el distrito a futuro ( No olvidarse )
    private var nombreDistri: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmOrderBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        ListDistrito()
        FindProductForId()
        InitRecyclerView( binding.rvOrderproduct )

        Listener()
    }
    private fun Listener(){
        binding.etDate.setOnClickListener { ShowDatePickerForm() }
    }
    private fun ShowDatePickerForm(){
        val datePicker = DatePickerFragment { anio, mes, dia -> onDateSelected( anio, mes, dia ) }
        datePicker.show( supportFragmentManager, "datePicker" )
    }
    private fun onDateSelected( anio: Int, mes: Int, dia: Int ){
        binding.etDate.setText( "$anio-$mes-$dia" )
    }
    private fun ListDistrito(){
        distritoViewModel.ListDistrito()
    }
    private fun BuscarDistrito( nombre: String ){
        distritoViewModel.BuscarDistrito( nombre )
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
        distritoViewModel.responseString.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spDistrito.adapter = adapter

                binding.spDistrito.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreDistri = it[ int ]
                        //Toast.makeText( this@ConfirmOrderActivity, nombreDistri, Toast.LENGTH_LONG ).show()
                        BuscarDistrito( nombreDistri!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
        distritoViewModel.responseDistritoMutableLiveData.observe( this, Observer {
            if( it != null ){
                distrito = it
            }
        } )
    }

    override fun ProductoDetail(precioTotal: Double) {
        binding.tvSubtotal.text = precioTotal.toString()
    }
}