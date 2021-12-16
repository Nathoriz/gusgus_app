package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.FragmentProfileBinding
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate( inflater, container, false )
        binding.txtperfil.text = Preferences.constantes.getClientName()
        Listener()

        return binding.root
    }
    private fun Listener(){
        binding.tvlogout.setOnClickListener { LogOut() }
        binding.ivlogout.setOnClickListener{ LogOut() }
        binding.ivperfil.setOnClickListener { GoPerfil() }
        binding.txtperfil.setOnClickListener { GoPerfil() }
        binding.ivpassword.setOnClickListener { ChangePassword() }
        binding.txtpassword.setOnClickListener { ChangePassword() }
    }
    private fun LogOut(){
        Preferences.constantes.saveBoolean( false )
        Preferences.constantes.saveRole( "" )
        Preferences.constantes.saveAdminName( "" )
        Preferences.constantes.saveClientName( "" )
        Preferences.constantes.saveTelefono( "" )
        Preferences.constantes.saveDireccion( "" )
        Preferences.constantes.saveTelefonoUser( "" )
        Preferences.constantes.saveDistrito( "" )
        val intent = Intent( context, LoginActivity::class.java)
        intent.flags = ( Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )
        startActivity( intent )
    }
    private fun GoPerfil(){
        val intent = Intent( context, UserDataActivity::class.java )
        startActivity( intent )
    }
    private fun ChangePassword(){
        val intent = Intent( context, ChangePasswordActivity::class.java )
        startActivity( intent )
    }
}