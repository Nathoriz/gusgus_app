package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
import mood.honeyprojects.gusgusapp.listeners.PisoListener
import mood.honeyprojects.gusgusapp.model.entity.Piso
import mood.honeyprojects.gusgusapp.view.adapter.CategoriaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.PisoAdapter
import mood.honeyprojects.gusgusapp.viewModel.DiametroViewModel
import mood.honeyprojects.gusgusapp.viewModel.PisoViewModel
import mood.honeyprojects.gusgusapp.viewModel.SaborViewModel

class PersonalizarActivity : AppCompatActivity(), PisoListener {
    private  lateinit var binding: ActivityPersonalizarBinding
    private lateinit var adapterpiso: PisoAdapter
    private val pisosViewModel: PisoViewModel by viewModels()

    private val diametroViewModel: DiametroViewModel by viewModels()
    private val saborViewModel: SaborViewModel by viewModels()

    private var diametroDescri: String?=null
    private var saborNombre: String?=null

    private val listapisos = mutableListOf<Piso>()
    private var idpiso:Long?=null

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
        GetAllDiametros()
        ViewModelDiametro()
        regresar()
    }

    private fun StepsPisos( numeroPisos: Int ){
        var i = 1
        while ( i <= numeroPisos ) {
            binding.llCake1Personalizar.visibility = View.VISIBLE
            binding.txtnombrepiso.text = "Piso $i"
            toast( i.toString() )
            i++
            break
        }
        do {
            var again = false
            binding.btnenviardatos.setOnClickListener {
             if( i <= numeroPisos ){
                 MostrarPiso( i )
                 binding.txtnombrepiso.text = "Piso $i"
                 again = true
                 toast( "enviado" )
                 i++
             }else{
                 again = false
                 toast( "Fue el ultimo paso" )
             }
         }
        } while ( again )

    }
    private fun MostrarPiso( numero: Int ){
        if( numero == 1 ){ binding.llCake1Personalizar.visibility = View.VISIBLE }
        if( numero == 2 ){ binding.llCake2Personalizar.visibility = View.VISIBLE }
        if( numero == 3 ){ binding.llCake3Personalizar.visibility = View.VISIBLE }
    }

    private fun GetAllDiametros(){
        diametroViewModel.listarNombreDiametro()
    }
    private fun GetListPisos(){
        pisosViewModel.getPisos()
    }
    private fun RecyclerViewPiso( rv: RecyclerView ){
        adapterpiso = PisoAdapter( listapisos, this )
        rv.layoutManager = LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false )
        rv.adapter = adapterpiso
    }

    private fun ViewModelSabor(){
        saborViewModel.listaNombreSabor.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spsaborpiso.adapter = adapter

                binding.spsaborpiso.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        saborNombre = it[ int ]
                        toast( saborNombre!! )
                        //Falta Buscar el diametro completo.
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
    }
    private fun ViewModelDiametro(){
        diametroViewModel.listaNombreDiametro.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spdiametropiso.adapter = adapter

                binding.spdiametropiso.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        diametroDescri = it[ int ]
                        toast( diametroDescri!! )
                        //Falta Buscar el diametro completo.
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
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

    override fun ListenerPiso( piso: Piso ) {
        idpiso = piso.id
        if( idpiso != null ){
            //Resetear tortas
            binding.llCake1Personalizar.visibility = View.GONE
            binding.llCake2Personalizar.visibility = View.GONE
            binding.llCake3Personalizar.visibility = View.GONE

            binding.imgSinopcionPersonalizar.visibility = View.GONE
            binding.tvSinopcionPersonalizar.visibility = View.GONE
            binding.cardpiso.visibility = View.VISIBLE
            binding.btnenviardatos.visibility = View.VISIBLE
            StepsPisos( idpiso?.toInt()!! )
        }
    }
    private fun toast( message: String ){
        Toast.makeText( this@PersonalizarActivity, message, Toast.LENGTH_SHORT ).show()
    }
}