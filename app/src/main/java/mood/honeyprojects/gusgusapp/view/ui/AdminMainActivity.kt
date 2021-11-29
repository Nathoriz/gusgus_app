package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivityAdminMainBinding
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences

class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminMainBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        Listener()
    }
    private fun Listener(){
        binding.btnlogout.setOnClickListener {
            LogOut()
        }
    }
    private fun LogOut(){
        Preferences.constantes.saveBoolean( false )
        Preferences.constantes.saveRole( "" )
        Preferences.constantes.saveAdminName( "" )
        Preferences.constantes.saveClientName( "" )
        Preferences.constantes.saveTelefono( "" )
        startActivity( Intent( applicationContext, LoginActivity::class.java ) )
    }
}