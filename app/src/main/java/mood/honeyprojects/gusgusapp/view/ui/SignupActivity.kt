package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.databinding.ActivitySignupBinding
import mood.honeyprojects.gusgusapp.listeners.ValiRegisterClient
import mood.honeyprojects.gusgusapp.model.entity.Cliente
import mood.honeyprojects.gusgusapp.model.requestEntity.UsuarioClient
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.ClienteViewModel

class SignupActivity : AppCompatActivity(), ValiRegisterClient {
    //Variables
    private lateinit var binding: ActivitySignupBinding
    private val clienteViewModel: ClienteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Metodos
        InitViewModel()
        Listeners()

    }
    private fun Listeners(){
        binding.btnRegisCliente.setOnClickListener {
            RegistrarClient()
        }
    }
    private fun RegistrarClient(){
        val cliente = Cliente(
            null,
            binding.txtNombre.text.toString(),
            binding.txtDireccion.text.toString(),
            binding.txttelefono.text.toString()
        )
        clienteViewModel.RegistrarClient( cliente, this )
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
    override fun ValiCliente(vali: Boolean) {
        if( vali ){
            Preferences.constantes.saveTelefono( binding.txttelefono.text.toString() )
            startActivity( Intent( this, Signup2Activity::class.java ) )
        }
    }
}