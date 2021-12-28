package mood.honeyprojects.gusgusapp.view.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.Observer
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityUserDataBinding
import mood.honeyprojects.gusgusapp.listeners.ValiUpdate
import mood.honeyprojects.gusgusapp.model.entity.Cliente
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.ClienteViewModel
import mood.honeyprojects.gusgusapp.viewModel.DistritoViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class UserDataActivity : AppCompatActivity(), ValiUpdate {
    private lateinit var binding: ActivityUserDataBinding
    private val clienteViewModel: ClienteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        InitViewModelTwo()
        InitViewModel()
        MostrarHora()
        ShowDataUser()
        Listener()

    }
    private fun Listener(){
        binding.btnEditarUdata.setOnClickListener { ActionEditPerfil( 1 ); BuscarClient(); InitViewModelTwo() }
        binding.btnEditarUdata.setOnLongClickListener { ActionEditPerfil( 2 ); true  }
        binding.btnGuardarUdata.setOnClickListener {
            ActualizarClient()
        }
    }

    private fun ShowDataUser(){
        binding.tvNombreshowUdata.text = Preferences.constantes.getClientName()
        binding.tvApellidoshowUdata.text = Preferences.constantes.getClientLastname()
        binding.tvTelefonoshowUdata.text = Preferences.constantes.getTelefonoUser()
    }

    private fun MostrarHora(){
        lifecycleScope.launch{
            val hora = withContext( Dispatchers.IO ){ HoraActual() }
            val hestado = hora.substring( hora.length - 2 )
            val hhora = hora.substring( 0, 2 ).toInt()

            if( hestado == "AM" ){
                binding.ivGrettingUdata.setImageResource( R.drawable.dia )
            }else if( hhora in 12..18 && hestado == "PM" ){
                binding.ivGrettingUdata.setImageResource( R.drawable.tarde )
            }else if( hhora in 19..23 || hhora.toString() == "00" && hestado == "PM" ){
                binding.ivGrettingUdata.setImageResource( R.drawable.noche )
            }
        }
    }

    fun BuscarClient(){
        clienteViewModel.BuscarCliente( Preferences.constantes.getIDCliente() )
    }

    fun ActualizarClient(){
        val client = Cliente(
            Preferences.constantes.getIDCliente(),
            binding.etNombreUdata.text.toString(),
            binding.etApellidoUdata.text.toString(),
            binding.etTelefonoUdata.text.toString()
        )
        clienteViewModel.ActualizarCliente( client, this )
    }

    private fun HoraActual(): String {
        val dateformat: DateFormat = SimpleDateFormat("HH:mm a")
        val formatDate = dateformat.format(Date()).toString()
        return formatDate.substring(formatDate.length - formatDate.length)
    }

    private fun ActionEditPerfil( abrir: Int ){
        if( abrir == 1 ){
            binding.cvContainershowUdata.visibility = View.GONE
            binding.cvContainereditUdata.visibility = View.VISIBLE
            binding.btnGuardarUdata.visibility = View.VISIBLE
        }else{
            binding.cvContainershowUdata.visibility = View.VISIBLE
            binding.cvContainereditUdata.visibility = View.GONE
            binding.btnGuardarUdata.visibility = View.GONE
        }
    }

    fun InitViewModelTwo(){
        clienteViewModel.responseClienteLiveData.observe( this, Observer {
            if( it != null ){
                binding.etNombreUdata.setText( it.nombre )
                binding.etApellidoUdata.setText( it.apellido )
                binding.etTelefonoUdata.setText( it.telefono )
            }
        } )
    }

    fun InitViewModel(){
        clienteViewModel.responseLiveData.observe( this, Observer {
            if( it != null ){
                ShowMessage( it )
            }
        } )
    }

    fun ShowMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }

    override fun valiUpdate(boolean: Boolean) {
        if( boolean ){
            binding.cvContainereditUdata.visibility = View.GONE
            binding.cvContainershowUdata.visibility = View.VISIBLE
        }
    }
}