package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.databinding.ActivityLoginBinding
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioLogin
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.UsuarioViewModel

class LoginActivity : AppCompatActivity() {
    //Variables
    private lateinit var binding: ActivityLoginBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        Listeners()
    }

    private fun Listeners(){
        binding.tvRegistrarseLogin.setOnClickListener {
            val intent = Intent( this, SignupActivity::class.java )
            startActivity( intent )
        }
        binding.btnIniciarsesionLogin.setOnClickListener {
            Login()
        }
    }

    private fun Login(){
        val usuarioLogin = UsuarioLogin( binding.etUsuarioLogin.text.toString(), binding.etContraseniaLogin.text.toString() )
        usuarioViewModel.Login( usuarioLogin )
    }

    private fun InitViewModel(){
        usuarioViewModel.messageLiveData.observe( this,  Observer {
            if( it == "CLIENTE" ){
                ShowMessage( "Bienvenido(a) ${ Preferences.constantes.getClientName() }" )
                startActivity( Intent( this, ClientMainActivity::class.java ) )
                finish()
            }
            if( it == "ADMIN" ){
                ShowMessage( "Bienvenido(a) ${ Preferences.constantes.getAdminName() }" )
                startActivity( Intent( this, AdminMainActivity::class.java ) )
                finish()
            }
        } )
        usuarioViewModel.messageErrorLiveData.observe( this, Observer {
            ShowMessage( it )
        } )
    }

    private fun ShowMessage( message: String ){
        Toast.makeText( applicationContext, message, Toast.LENGTH_LONG ).show()
    }
}