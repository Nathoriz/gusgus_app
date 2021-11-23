package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.databinding.ActivitySignup2Binding
import mood.honeyprojects.gusgusapp.listeners.ValiRegisterClient
import mood.honeyprojects.gusgusapp.listeners.ValiRegisterUsuario
import mood.honeyprojects.gusgusapp.model.entity.Cliente
import mood.honeyprojects.gusgusapp.model.entity.Rol
import mood.honeyprojects.gusgusapp.model.entity.Usuario
import mood.honeyprojects.gusgusapp.model.requestEntity.Client
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioClient
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.ClienteViewModel

class Signup2Activity : AppCompatActivity(), ValiRegisterUsuario {
    //Variables
    private lateinit var binding: ActivitySignup2Binding
    private val clienteViewModel: ClienteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup2Binding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        Listeners()
    }
    private fun RegistrarUsuarioCliente(){
        val cliente = Client( Preferences.constantes.getTelefono() )
        val usuario = UsuarioClient(
            cliente,
            binding.textpassword.text.toString(),
            binding.txtUsername.text.toString()
        )
        if( ValidarContrasenia() ){
            clienteViewModel.RegistrarUsuario( usuario, this )
        }
    }
    private fun ValidarContrasenia():Boolean{
        if( !binding.txtPassword2.text.toString().equals(binding.textpassword.text.toString()) ){
            ShowMessage( "Asegurate que la contraseña sea correcta." )
            return false
        }
        return true
    }
    private fun Listeners(){
        binding.btnRegisUsuario.setOnClickListener { RegistrarUsuarioCliente() }
    }
    private fun InitViewModel(){
        clienteViewModel.responseLiveData.observe( this,  Observer {
            if( it != null ){
                Toast.makeText( this, it, Toast.LENGTH_SHORT ).show()
            }else{
                Toast.makeText( this, "Error Server", Toast.LENGTH_SHORT ).show()
            }
        } )
    }
    override fun ValiUsuario(vali: Boolean) {
        if( vali ){
            startActivity( Intent( this, LoginActivity::class.java ).addFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK ) )
        }
    }
    private fun ShowMessage( message: String ){
        Toast.makeText( applicationContext, message, Toast.LENGTH_LONG ).show()
    }
}