package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityClientMenuBinding

class ClientMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientMenuBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityClientMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.clientmenu

        val navController = findNavController(R.id.nav_host_fragment_activity_principal)


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_gusgus, R.id.navigation_search, R.id.navigation_orders,R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.background = null
        config()
    }

    private fun config(){
        setExitSharedElementCallback( MaterialContainerTransformSharedElementCallback() )
        window.sharedElementsUseOverlay = false
    }
    fun go( view: View) {
        val intent = Intent( this, PersonalizarActivity::class.java )
        val bundle = ActivityOptions.makeSceneTransitionAnimation( this, binding.fabperso, "go" ).toBundle()
        startActivity( intent, bundle )
    }
}