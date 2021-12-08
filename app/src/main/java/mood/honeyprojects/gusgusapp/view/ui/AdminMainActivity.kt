package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mood.honeyprojects.gusgusapp.databinding.ActivityAdminMainBinding
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        ShowUserAdmin()
        MostrarHora()
        Listener()
    }
    private fun Listener(){
        binding.txtlogout.setOnClickListener { LogOut() }
    }
    private fun MostrarHora(){
        lifecycleScope.launch{
            val hora = withContext( Dispatchers.IO ){ HoraActual() }
            val hestado = hora.substring( hora.length - 2 )
            val hhora = hora.substring( 0, 2 ).toInt()

            if( hestado == "AM" ){
                binding.tvhora.text = "Buenos Días"
            }else if( hhora in 12..18 && hestado == "PM" ){
                binding.tvhora.text = "Buenas Tardes"
            }else if( hhora in 19..23 || hhora.toString() == "00" && hestado == "PM" ){
                binding.tvhora.text = "Buenas Noches"
            }
        }
    }
    private fun HoraActual(): String {
        val dateformat: DateFormat = SimpleDateFormat("HH:mm a")
        val formatDate = dateformat.format(Date()).toString()
        return formatDate.substring(formatDate.length - formatDate.length)
    }
    private fun ShowUserAdmin() {
        binding.txtNameAdmin.text = "Hola ${Preferences.constantes.getAdminName()}"
    }
    private fun LogOut(){
        Preferences.constantes.saveBoolean( false )
        Preferences.constantes.saveRole( "" )
        Preferences.constantes.saveAdminName( "" )
        Preferences.constantes.saveClientName( "" )
        Preferences.constantes.saveTelefono( "" )
        startActivity( Intent( applicationContext, LoginActivity::class.java ) )
        finish()
    }
}