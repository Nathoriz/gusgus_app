package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityUserDataBinding
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class UserDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        MostrarHora()
        ShowDataUser()
        Listener()
    }
    private fun Listener(){
        binding.btneditar.setOnClickListener { ActionEditPerfil( 1 ) }
        binding.btneditar.setOnLongClickListener { ActionEditPerfil( 2 ); true  }
    }
    private fun ShowDataUser(){
        binding.txtuser.text = Preferences.constantes.getClientName()
        binding.tvNombrecompleto.text = Preferences.constantes.getClientName()
        binding.tvTelefono.text = Preferences.constantes.getTelefonoUser()
        binding.tvDireccion.text = Preferences.constantes.getDireccion()
    }
    private fun MostrarHora(){
        lifecycleScope.launch{
            val hora = withContext( Dispatchers.IO ){ HoraActual() }
            val hestado = hora.substring( hora.length - 2 )
            val hhora = hora.substring( 0, 2 ).toInt()

            if( hestado == "AM" ){
                binding.ivGrettingimage.setImageResource( R.drawable.dia )
            }else if( hhora in 12..18 && hestado == "PM" ){
                binding.ivGrettingimage.setImageResource( R.drawable.tarde )
            }else if( hhora in 19..23 || hhora.toString() == "00" && hestado == "PM" ){
                binding.ivGrettingimage.setImageResource( R.drawable.noche )
            }
        }
    }
    private fun HoraActual(): String {
        val dateformat: DateFormat = SimpleDateFormat("HH:mm a")
        val formatDate = dateformat.format(Date()).toString()
        return formatDate.substring(formatDate.length - formatDate.length)
    }
    private fun ActionEditPerfil( abrir: Int ){
        if( abrir == 1 ){
            binding.cvContainershowuserdata.visibility = View.GONE
            binding.cvContaineruserdatafield.visibility = View.VISIBLE
            binding.btnGuardaredituser.visibility = View.VISIBLE
        }else{
            binding.cvContainershowuserdata.visibility = View.VISIBLE
            binding.cvContaineruserdatafield.visibility = View.GONE
            binding.btnGuardaredituser.visibility = View.GONE
        }
    }
}