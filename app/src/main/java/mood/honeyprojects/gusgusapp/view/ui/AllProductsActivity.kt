package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivityAllProductsBinding

class AllProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}