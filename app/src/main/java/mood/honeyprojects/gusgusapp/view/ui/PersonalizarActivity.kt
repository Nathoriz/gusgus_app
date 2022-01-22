package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityPersonalizarBinding
import mood.honeyprojects.gusgusapp.model.entity.Piso
import mood.honeyprojects.gusgusapp.view.adapter.CategoriaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.PisoAdapter
import mood.honeyprojects.gusgusapp.viewModel.PisoViewModel

class PersonalizarActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityPersonalizarBinding
    private lateinit var adapterpiso: PisoAdapter
    private val pisosViewModel: PisoViewModel by viewModels()

    private val listapisos = mutableListOf<Piso>()

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

        GetListPisos()
        ViewModelPiso()
        RecyclerViewPiso( binding.rvOpcionpisoPersonalizar )
        regresar()
    }
    private fun GetListPisos(){
        pisosViewModel.getPisos()
    }
    private fun RecyclerViewPiso( rv: RecyclerView ){
        adapterpiso = PisoAdapter( listapisos )
        rv.layoutManager = LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false )
        rv.adapter = adapterpiso
    }
    private fun ViewModelPiso(){
        pisosViewModel.responsePiso.observe( this, Observer {
            if( it != null ){
                listapisos.addAll( it )
                adapterpiso.notifyDataSetChanged()
            }
        } )
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
        binding.ivBackPersonalizar.setOnClickListener {
            val bundle = ActivityOptions.makeSceneTransitionAnimation( this, binding.ivBackPersonalizar, "go" ).toBundle()
            val intent = Intent( this, ClientMainActivity::class.java )
            startActivity( intent, bundle )
        }
    }
}