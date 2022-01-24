package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
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
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.view.adapter.CategoriaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.PisoAdapter
import mood.honeyprojects.gusgusapp.viewModel.DiametroViewModel
import mood.honeyprojects.gusgusapp.viewModel.PisoViewModel
import mood.honeyprojects.gusgusapp.viewModel.RellenoViewModel
import mood.honeyprojects.gusgusapp.viewModel.SaborViewModel

class PersonalizarActivity : AppCompatActivity(), PisoListener {
    private  lateinit var binding: ActivityPersonalizarBinding
    private lateinit var adapterpiso: PisoAdapter
    private val pisosViewModel: PisoViewModel by viewModels()

    private val diametroViewModel: DiametroViewModel by viewModels()
    private val saborViewModel: SaborViewModel by viewModels()
    private val rellenoViewModel: RellenoViewModel by viewModels()

    private var diametroDescri: String?=null
    private var saborNombre: String?=null
    private var saborColor: String?=null
    private var rellenoNombre: String?=null

    private val listapisos = mutableListOf<Piso>()
    private var idpiso:Long?=null
    private var i = 1

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
        GetAllSabor()
        ViewModelSabor()
        GetAllRelleno()
        ViewModelRelleno()
        regresar()
    }

    private fun StepsPisos( numeroPisos: Int ){
        do {
            var again = false
            MostrarPiso( i )
            binding.txtnombrepiso.text = "Piso $i"
            i++
            binding.btnenviardatos.setOnClickListener {
             if( i <= numeroPisos ){
                 binding.txtnombrepiso.text = "Piso $i"
                 MostrarPiso( i )
                 again = true
                 toast( "enviado" )
                 i++
             }else{
                 again = false
                 toast( "Fue el ultimo paso" )
                 i = 1
             }
         }
        } while ( again )

    }
    private fun ShowDiametroPisos(){

    }
    private fun ShowColorPisos( colors: String ){
        val sabor = "#$colors"
        var ty = i - 1
        val color = Color.parseColor( sabor )
        if( ty ==  1 ){
            binding.llCake1Personalizar.setImageResource(R.drawable.ic_colo_sabo)
            binding.llCake1Personalizar.setColorFilter( color )
        }else if ( ty == 2 ){
            binding.llCake2Personalizar.setImageResource(R.drawable.ic_colo_sabo)
            binding.llCake2Personalizar.setColorFilter( color )
        }else if( ty == 3 ){
            binding.llCake3Personalizar.setImageResource(R.drawable.ic_colo_sabo)
            binding.llCake3Personalizar.setColorFilter( color )
        }else{
            binding.llCake1Personalizar.setColorFilter( R.color.dkgreen )
        }
    }
    private fun MostrarPiso( numero: Int ){
        if( numero == 1 ){ binding.llCake1Personalizar.visibility = View.VISIBLE }
        if( numero == 2 ){ binding.llCake2Personalizar.visibility = View.VISIBLE }
        if( numero == 3 ){ binding.llCake3Personalizar.visibility = View.VISIBLE }
    }

    private fun GetAllRelleno(){
        rellenoViewModel.listarNombreRelleno()
    }
    private fun GetAllSabor(){
        saborViewModel.listarNombreSabor()
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

    private fun ViewModelRelleno(){
        rellenoViewModel.listaDescripcionRelleno.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sprellenopiso.adapter = adapter

                binding.sprellenopiso.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        rellenoNombre = it[ int ]
                        toast( rellenoNombre!! )
                        //Falta Buscar el diametro completo.
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
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
                        saborViewModel.BuscarSaborNombre( saborNombre!! )

                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
        saborViewModel.saborLiveData.observe( this, Observer {
            if( it != null ){
                saborColor = it.color
                ShowColorPisos( saborColor!! )
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
            i = 1
            StepsPisos( idpiso?.toInt()!! )
        }
    }
    private fun toast( message: String ){
        Toast.makeText( this@PersonalizarActivity, message, Toast.LENGTH_SHORT ).show()
    }
}