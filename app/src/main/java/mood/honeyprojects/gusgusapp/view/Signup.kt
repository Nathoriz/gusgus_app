package mood.honeyprojects.gusgusapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivitySignupBinding

class signup : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}