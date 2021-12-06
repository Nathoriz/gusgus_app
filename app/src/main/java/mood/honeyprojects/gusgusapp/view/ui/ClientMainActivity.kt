package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityClientMainBinding

class ClientMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClientMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_principal)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gusGusFragment, R.id.searchFragment, R.id.ordersFragment, R.id.profileFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.background = null
        configuration()
    }

    private fun configuration(){
        setExitSharedElementCallback( MaterialContainerTransformSharedElementCallback() )
        window.sharedElementsUseOverlay = false
    }
    fun go( view: View) {
        val intent = Intent( this, PersonalizarActivity::class.java )
        val option = ActivityOptionsCompat.makeSceneTransitionAnimation( this, binding.floatingActionButton, binding.floatingActionButton.transitionName )
        //val bundle = ActivityOptions.makeSceneTransitionAnimation( this, binding.floatingActionButton, "go" ).toBundle()
        startActivity( intent, option.toBundle() )
    }
}