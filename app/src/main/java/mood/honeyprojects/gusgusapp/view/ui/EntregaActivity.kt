package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivityEntregaBinding

class EntregaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEntregaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  ActivityEntregaBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        Listener()
    }
    private fun Listener(){
        binding.ibCloseEntrega.setOnClickListener { closeActivity() }
    }
    private fun closeActivity(){
        val intent = Intent( this, DetailProductActivity::class.java )
        startActivity( intent )
        finish()
    }
}