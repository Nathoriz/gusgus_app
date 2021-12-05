package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityPersonalizarBinding

class PersonalizarActivity : AppCompatActivity() {
    //lateinit var buttonRegresar : TextView
    private  lateinit var binding: ActivityPersonalizarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizarBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        config()
        regresar()
    }

    private fun config(){
        //findViewById<View>(android.R.id.content).transitionName = "go"
        binding.root.transitionName = "go"
        setEnterSharedElementCallback( MaterialContainerTransformSharedElementCallback() )

        val transform = MaterialContainerTransform()
        transform.addTarget( binding.root )
        transform.duration = 500

        window.sharedElementEnterTransition = transform
        window.sharedElementReturnTransition = transform
    }
    private fun regresar(){
        //buttonRegresar = findViewById( R.id.btnregresar )
        binding.btnregresar.setOnClickListener {
            val bundle = ActivityOptions.makeSceneTransitionAnimation( this, binding.btnregresar, "go" ).toBundle()
            val intent = Intent( this, ClientMenuActivity::class.java )
            startActivity( intent, bundle )
        }
    }
}