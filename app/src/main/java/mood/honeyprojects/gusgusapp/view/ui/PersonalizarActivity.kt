package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityPersonalizarBinding

class PersonalizarActivity : AppCompatActivity() {
    //lateinit var buttonRegresar : TextView
    private  lateinit var binding: ActivityPersonalizarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature( Window.FEATURE_ACTIVITY_TRANSITIONS )
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalizarBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        setEnterSharedElementCallback( MaterialContainerTransformSharedElementCallback() )
        window.sharedElementEnterTransition = configTransition()
        window.sharedElementExitTransition = configTransition()
        window.sharedElementReenterTransition = configTransition()

        regresar()
    }
    private fun configTransition(): MaterialContainerTransform{
        return MaterialContainerTransform().apply {
            addTarget( R.id.contain )
            setAllContainerColors( MaterialColors.getColor( findViewById( R.id.contain ), R.attr.colorSurface ))
            duration = 500
            pathMotion = MaterialArcMotion()
            interpolator = FastOutSlowInInterpolator()
            fadeMode = MaterialContainerTransform.FADE_MODE_IN
        }
    }
    private fun regresar(){
        //buttonRegresar = findViewById( R.id.btnregresar )
        binding.btnregresar.setOnClickListener {
            val bundle = ActivityOptions.makeSceneTransitionAnimation( this, binding.btnregresar, "go" ).toBundle()
            val intent = Intent( this, ClientMainActivity::class.java )
            startActivity( intent, bundle )
        }
    }
}