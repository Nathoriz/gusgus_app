package mood.honeyprojects.gusgusapp.view.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityPersonalizarBinding
import mood.honeyprojects.gusgusapp.listeners.PisoListener
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.PisoAdapter
import mood.honeyprojects.gusgusapp.viewModel.*

class PersonalizarActivity : AppCompatActivity(), PisoListener {
    private  lateinit var binding: ActivityPersonalizarBinding
    private lateinit var adapterpiso: PisoAdapter
    private val pisosViewModel: PisoViewModel by viewModels()

    private val diametroViewModel: DiametroViewModel by viewModels()
    private val saborViewModel: SaborViewModel by viewModels()
    private val rellenoViewModel: RellenoViewModel by viewModels()
    private val personalizacionViewModel: PersonalizacionViewModel by viewModels()
    private val cubiertaViewModel: CubiertaViewModel by viewModels()
    private val personalizacionPisoViewModel: PersonalizacionPisoViewModel by viewModels()

    private var diametroDescri: String?=null
    private var saborNombre: String?=null
    private var saborColor: String?=null
    private var rellenoNombre: String?=null
    private var idCubierta: Long?=null

    private var diametro: Diametro?=null
    private var sabor: Sabor?=null
    private var relleno: Relleno?=null
    private var personali: Personalizacion?=null

    private val listapisos = mutableListOf<Piso>()
    private val listprecios = mutableListOf<Double>()
    private var idpiso: Long?=null
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

        binding.ivpresentation.visibility = View.GONE
        GetListPisos()
        ViewModelPiso()
        RecyclerViewPiso( binding.rvOpcionpisoPersonalizar )
        GetAllDiametros()
        ViewModelDiametro()
        GetAllSabor()
        ViewModelSabor()
        GetAllRelleno()
        ViewModelRelleno()
        ViewModelCubierta()
        ViewModelPersonalizacion()
        ViewModelPersonalizacionPiso()
        regresar()
        Listener()
    }
    private fun Listener(){
        binding.btnFondamPersonalizacion.setOnClickListener { cubiertaViewModel.GetCubiertaByNombre( "fondam" ) }
        binding.btnChantillyPersonalizacion.setOnClickListener { cubiertaViewModel.GetCubiertaByNombre( "chantilly" ) }
        binding.btnCrearPersonalizacion.setOnClickListener {
            if( ValidarPersonalizacion() ){
                lifecycleScope.launch {
                    val response = async(Dispatchers.IO) { RegistrarPersonalizacion() }
                    if( response.await() ){
                        binding.cvCreacionpersoPersonalizacion.visibility = View.GONE
                        toast( "Registro Creado." )
                    }
                }
            }
        }
        binding.btnterminar.setOnClickListener {
            val intent = Intent( this, ClientMainActivity::class.java )
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
    }
    private fun StepsPisos( numeroPisos: Int ) {
        do {
            var again = false
            MostrarPiso( i )
            binding.txtnombrepiso.text = "Piso $i"
            i++
            binding.btnenviardatos.setOnClickListener {
             if( i <= numeroPisos ){
                 binding.txtnombrepiso.text = "Piso $i"
                 MostrarPiso( i )
                 if( ValidarPersPiso() ){
                     lifecycleScope.launch {
                         val response = async( Dispatchers.IO ) { RegistrarPersPiso() }
                         if( response.await() ){
                            toast( "Piso Registrado" )
                             again = true
                             toast( "enviado" )
                             i++
                         }
                     }
                 }

             }else{
                 again = false
                 lifecycleScope.launch {
                     val response = async( Dispatchers.IO ) { RegistrarPersPiso() }
                     if( response.await() ){
                         val id = personali?.id
                         val precio = listprecios.sum()
                         toast( "Fue el ultimo paso, Piso Registrado." )
                         personalizacionViewModel.ActualizarPrecio( id!!, precio )
                         binding.btnenviardatos.visibility = View.GONE
                         binding.btnterminar.visibility = View.VISIBLE
                         binding.rvOpcionpisoPersonalizar.visibility = View.GONE
                     }
                 }
             }
            }
        } while ( again )
    }

    private fun RegistrarPersPiso(): Boolean {
        val piso = Piso( idpiso, null )
        val persPiso = PersonalizacionPiso( 0, personali, piso, sabor, relleno, diametro, diametro?.precio?.toDouble() )
        personalizacionPisoViewModel.RegistrarPersPiso( persPiso )
        listprecios.add( diametro?.precio?.toDouble()!! )
        return true
    }
    private fun ValidarPersPiso(): Boolean {
        if( diametro == null ){
            toast( "Seleccione un diametro" )
            return false
        }else if( sabor == null ){
            toast( "Seleccione un sabor" )
            return false
        }else if( relleno == null ){
            toast( "Seleccione relleno." )
            return false
        }else{
            return true
        }
    }
    private fun RegistrarPersonalizacion(): Boolean {
        val nombre = binding.etNombrePersonalizacion.text.toString()
        val cubierta = Cubierta( idCubierta, null )
        val cliente = Cliente( Preferences.constantes.getIDCliente(), null, null, null )
        val personalizacion = Personalizacion( 0, nombre, "https://i.imgur.com/v43yKLg.png", cubierta, 0.0, cliente )
        personalizacionViewModel.RegistrarPeronalizacion( personalizacion )
        return true
    }
    private fun ValidarPersonalizacion(): Boolean {
        if( binding.etNombrePersonalizacion.text.toString().isEmpty() ){
            toast( "Ingrese el nombre." )
            return false
        }else if( idCubierta == null ){
            toast( "Seleccione una cubierta." )
            return false
        }else{
            return true
        }
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

    private fun ViewModelPersonalizacionPiso(){
        personalizacionPisoViewModel.responseDataPersPiso.observe( this, Observer {
            if( it != null ){
                toast( it.id.toString() )
            }
        } )
    }
    private fun ViewModelPersonalizacion(){
        personalizacionViewModel.responsePersonalizacion.observe( this, Observer {
            if( it != null ) {
                personali = it
            }
        } )
    }
    private fun ViewModelCubierta(){
        cubiertaViewModel.cubiertaLiveData.observe( this, Observer {
            if( it != null ){
                binding.txtcubierta.visibility = View.VISIBLE
                binding.txtcubierta.text = it.nombre
                idCubierta = it.id
            }
        } )
    }
    private fun ViewModelRelleno(){
        rellenoViewModel.listaDescripcionRelleno.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.sprellenopisos.adapter = adapter

                binding.sprellenopisos.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        rellenoNombre = it[ int ]
                        toast( rellenoNombre!! )
                        rellenoViewModel.GetRellenoByDescrip( rellenoNombre!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
        rellenoViewModel.rellenoLiveData.observe( this, Observer {
            if( it != null ){
                relleno = it
            }
        } )
    }
    private fun ViewModelSabor(){
        saborViewModel.listaNombreSabor.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spsaborpisos.adapter = adapter

                binding.spsaborpisos.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
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
                sabor = it
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
                binding.spdiametropisos.adapter = adapter

                binding.spdiametropisos.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        diametroDescri = it[ int ]
                        toast( diametroDescri!! )
                        diametroViewModel.GetDiametroByDescrip( diametroDescri!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
        diametroViewModel.diametroLiveData.observe( this, Observer {
            if( it != null ){
                diametro = it
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
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
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
            binding.ivpresentation.visibility = View.VISIBLE
            toast( "Seleccionó $idpiso pisos" )
            i = 1
            StepsPisos( idpiso?.toInt()!! )
        }
    }
    private fun toast( message: String ){
        Toast.makeText( this@PersonalizarActivity, message, Toast.LENGTH_SHORT ).show()
    }
}