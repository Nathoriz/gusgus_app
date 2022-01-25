package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivityMantRecetaBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantRecetaInsumoBinding

class MantRecetaInsumoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMantRecetaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}