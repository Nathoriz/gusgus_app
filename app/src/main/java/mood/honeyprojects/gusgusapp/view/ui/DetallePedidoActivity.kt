package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivityConfirmOrderBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityDetallePedidoBinding

class DetallePedidoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePedidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}