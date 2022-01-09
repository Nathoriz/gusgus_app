package mood.honeyprojects.gusgusapp.view.ui

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.classes.DatePickerFragment
import mood.honeyprojects.gusgusapp.classes.TimePickerFragment
import mood.honeyprojects.gusgusapp.databinding.ActivityEntregaBinding
import mood.honeyprojects.gusgusapp.viewModel.DistritoViewModel
import mood.honeyprojects.gusgusapp.viewModel.EntregaViewModel

class EntregaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntregaBinding
    private val distritoViewModel: DistritoViewModel by viewModels()
    private val entregaViewModel: EntregaViewModel by viewModels()

    private var nombreDistri: String?=null
    private var opcion = 0
    private var opcionDateANdTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityEntregaBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        ViewModelDitrito()
        ListDistrito()
        Listener()
    }
    private fun Listener(){
        binding.ibCloseEntrega.setOnClickListener { closeThisActivity() }
        binding.cvDelivery.setOnClickListener { AbrirOpcionDeEntrega( View.VISIBLE ); opcion = 1 }
        binding.cvStore.setOnClickListener { AbrirOpcionDeDelivery( View.VISIBLE ); opcion = 0 }
        binding.clOpciones.setOnClickListener {
            if( opcion == 1 ){
                AbrirOpcionDeEntrega( View.GONE )
            }else if( opcion == 0 ){
                AbrirOpcionDeDelivery( View.GONE )
            }
         }
        binding.etDatedelveryEntrega.setOnClickListener { ShowDatePickerForm(); opcionDateANdTime = 1 }
        binding.etTimedeliveryEntrega.setOnClickListener { ShowTimePickerForm(); opcionDateANdTime = 1 }
        binding.ivDate.setOnClickListener { ShowDatePickerForm(); opcionDateANdTime = 2 }
        binding.ivTIme.setOnClickListener { ShowTimePickerForm(); opcionDateANdTime = 2 }

    }
    private fun ShowDatePickerForm(){
        val datePicker = DatePickerFragment { anio, mes, dia -> onDateSelected( anio, mes, dia ) }
        datePicker.show( supportFragmentManager, "datePicker" )
    }
    private fun ShowTimePickerForm(){
        val timePicker = TimePickerFragment { onTimeSelected( it ) }
        timePicker.show( supportFragmentManager, "time" )
    }
    private fun onTimeSelected( time: String ){
        if( opcionDateANdTime == 1 ){//1 es Click de formulario delivery
            binding.etTimedeliveryEntrega.setText( time )
        }
        if( opcionDateANdTime == 2 ){//2 es Click de formulario recoger tienda
            binding.etTimestoreEntrega.setText( time )
        }

    }
    private fun onDateSelected( anio: Int, mes: Int, dia: Int ){
        if( opcionDateANdTime == 1 ){//1 es Click de formulario delivery
            binding.etDatedelveryEntrega.setText( "$anio-${mes+1}-$dia" )
        }
        if( opcionDateANdTime == 2 ){//2 es Click de formulario recoger tienda
            binding.etDatestoreEntrega.setText( "$anio-${mes+1}-$dia" )
        }
    }
    private fun closeThisActivity(){
        val intent = Intent( this, DetailProductActivity::class.java )
        startActivity( intent )
        finish()
    }
    private fun AbrirOpcionDeEntrega( int: Int ){
        InvisibleOpcionRecogerTienda( View.GONE )
        InvisibleOpcionDelivery( View.GONE )
        if( int == 8 ){
            InvisibleOpcionRecogerTienda( View.VISIBLE )
            InvisibleOpcionDelivery( View.VISIBLE )
        }
        binding.cvStoreEntrega.visibility = int
        binding.btnStoreEntrega.visibility = int
    }
    private fun AbrirOpcionDeDelivery( int: Int ){
        InvisibleOpcionRecogerTienda( View.GONE )
        InvisibleOpcionDelivery( View.GONE )
        if( int == 8 ){
            InvisibleOpcionRecogerTienda( View.VISIBLE )
            InvisibleOpcionDelivery( View.VISIBLE )
        }
        binding.cvDeliveryEntrega.visibility = int
        binding.btnDeliveryEntrega.visibility = int
    }
    private fun ListDistrito(){
        distritoViewModel.ListDistrito()
    }

    private fun InvisibleOpcionRecogerTienda( int: Int ){
        binding.cvDelivery.visibility = int
        binding.textViewrecogertienda.visibility = int
    }
    private fun InvisibleOpcionDelivery( int: Int ){
        binding.cvStore.visibility = int
        binding.textViewdelivery.visibility = int
    }

    private fun ViewModelDitrito(){
        distritoViewModel.responseString.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spDistritodeliveryEntrega.adapter = adapter

                binding.spDistritodeliveryEntrega.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreDistri = it[ int ]
                        Toast.makeText( this@EntregaActivity, nombreDistri, Toast.LENGTH_LONG ).show()
                        //BuscarDistrito( nombreDistri!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
    }
}