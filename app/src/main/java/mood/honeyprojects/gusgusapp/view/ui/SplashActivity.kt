package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mood.honeyprojects.gusgusapp.databinding.ActivitySplashBinding
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate( layoutInflater )
        setContentView(binding.root)
        supportActionBar?.hide()
        Splash()
    }
    private fun Splash(){
        CoroutineScope( Dispatchers.Main ).launch {
            delay( 5000L )
            if( Preferences.constantes.getBoolean() && Preferences.constantes.getRole() == "CLIENTE" ){
                startActivity( Intent( applicationContext, ClientMenuActivity::class.java) )
            }
            if( Preferences.constantes.getBoolean() && Preferences.constantes.getRole() == "ADMIN" ){
                startActivity( Intent( applicationContext, AdminMainActivity::class.java) )
            }
            if( !Preferences.constantes.getBoolean() ){
                    startActivity( Intent( applicationContext, LoginActivity::class.java) )
            }

        }

    }

}