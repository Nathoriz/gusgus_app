package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityClientMenuBinding

class ClientMenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClientMenuBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navView: BottomNavigationView = binding.clientmenu
        navView.background = null
        navView.setBackgroundColor(0)
        replaceFragment(GusGusFragment())
        
        navView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.inicio -> replaceFragment(GusGusFragment())
                R.id.buscar -> replaceFragment(SearchFragment())
                R.id.pedidos -> replaceFragment(OrdersFragment())
                R.id.perfil -> replaceFragment(ProfileFragment())
            }
            true
        }
        config()
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment!= null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment_activity_principal,fragment)
            transaction.commit()
        }
    }
    private fun config(){
        setExitSharedElementCallback( MaterialContainerTransformSharedElementCallback() )
        window.sharedElementsUseOverlay = false
    }
    fun go( view: View) {
        val intent = Intent( this, PersonalizarActivity::class.java )
        val bundle = ActivityOptions.makeSceneTransitionAnimation( this, binding.fabPerso, "go" ).toBundle()
        startActivity( intent, bundle )
    }
}