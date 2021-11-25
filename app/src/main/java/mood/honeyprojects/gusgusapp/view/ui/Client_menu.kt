package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityClientMenuBinding

class client_menu : AppCompatActivity() {
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
    }

    private fun replaceFragment(fragment: Fragment) {
        if(fragment!= null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragcontainer,fragment)
            transaction.commit()
        }
    }
}