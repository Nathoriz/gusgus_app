package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.databinding.ActivityChangePasswordBinding
import mood.honeyprojects.gusgusapp.model.requestEntity.PasswordResponseUpdate
import mood.honeyprojects.gusgusapp.model.requestEntity.PasswordResponseVali
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.UsuarioViewModel

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private val usuarioViewModel: UsuarioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModels()
        Listeners()
    }

    private fun Listeners(){
        binding.btnConfirnarChngpassword.setOnClickListener { ValidarPassword() }
        binding.btnGuardarChngpassword.setOnClickListener { UpdatePassword() }
    }
    private fun UpdatePassword(){
        val updatePassword = PasswordResponseUpdate(
            Preferences.constantes.getIDCliente(),
            binding.etNuevacontraseniaChngpassword.text.toString(),
            binding.etConfnuevacontraseniaChngpassword.text.toString()
        )
        usuarioViewModel.UpdatePassword( updatePassword )
    }
    private fun ValidarPassword(){
        val password = PasswordResponseVali(
            Preferences.constantes.getIDCliente(),
            binding.etConfcontraseniaChngpassword.text.toString()
        )
        usuarioViewModel.ValidarPassword( password )
    }
    private fun InitViewModels(){
        usuarioViewModel.messageResponseValiPassword.observe( this, Observer {
            if( it != null ){
                if( it.estado == true ){
                    binding.clValidateChngpassword.visibility = View.GONE
                    binding.clUpdateChngpassword.visibility = View.VISIBLE
                    binding.etConfcontraseniaChngpassword.setText( "" )
                    ShowMessage( it.message.toString() )
                }
            }
        } )
        usuarioViewModel.messageErrorLiveData.observe( this, Observer {
            if( it != null ){
                ShowMessage( it )
            }
        } )
        usuarioViewModel.messageResponseUpdatePassword.observe( this, Observer {
            if( it != null ){
                if( it.estado == true ){
                    binding.clUpdateChngpassword.visibility = View.GONE
                    binding.clValidateChngpassword.visibility = View.VISIBLE
                    binding.etNuevacontraseniaChngpassword.setText( "" )
                    binding.etConfnuevacontraseniaChngpassword.setText( "" )
                    binding.etNuevacontraseniaChngpassword.requestFocus()
                    ShowMessage( it.message.toString() )
                }
            }
        } )
    }
    private fun ShowMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}