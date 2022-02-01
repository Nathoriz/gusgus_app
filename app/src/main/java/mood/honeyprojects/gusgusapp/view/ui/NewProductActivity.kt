package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import mood.honeyprojects.gusgusapp.databinding.ActivityNewProductBinding
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.model.requestEntity.ProductoResponse
import mood.honeyprojects.gusgusapp.view.adapter.*
import mood.honeyprojects.gusgusapp.viewModel.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

class NewProductActivity : AppCompatActivity(), SelectRecetaAdapter.OnClickRecetaListener,MultipleSelectSaborAdapter.OnClickSaborListener,MultipleSelectDiametroAdapter.OnClickDiametroListener,MultipleSelectRellenoAdapter.OnClickRellenoListener {
    private lateinit var binding:ActivityNewProductBinding

    private lateinit var imgreferences: DatabaseReference
    private lateinit var storageReference: StorageReference

    private var encodeImage: ByteArray?=null
    private var url = ""

    private val visibilidadViewModel: VisibilidadViewModel by viewModels()
    private var visibilidad: Visibilidad?=null

    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private val listaNombreCategoria = mutableListOf<String>()
    private var categoria: Categoria? = null
    private var nombreCategoria: String = ""

    private lateinit var recetaAdapter: SelectRecetaAdapter
    private val recetaViewModel: RecetaViewModel by viewModels()
    private val listaReceta = mutableListOf<Receta>()
    private var receta: Receta? = null

    private val productoViewModel: ProductoViewModel by viewModels()
    private var producto: Producto? = null

    private val cubiertaViewModel: CubiertaViewModel by viewModels()
    private val listaNombreCubierta = mutableListOf<String>()
    private var cubierta: Cubierta? = null
    private var nombreCubierta: String = ""

    private val alturaViewModel: AlturaViewModel by viewModels()
    private val listaNombreAltura = mutableListOf<String>()
    private var altura: Altura? = null
    private var nombreAltura: String = ""

    private lateinit var saborAdapter: MultipleSelectSaborAdapter
    private val saborViewModel: SaborViewModel by viewModels()
    private val listaSabor = mutableListOf<Sabor>()
    private var listaNombreSabor = mutableListOf<String>()
    private var sabor: Sabor? = null

    private lateinit var diametroAdapter: MultipleSelectDiametroAdapter
    private val diametroViewModel: DiametroViewModel by viewModels()
    private val listaDiametro = mutableListOf<Diametro>()
    private var listaNombreDiametro = mutableListOf<String>()
    private var diametro: Diametro? = null

    private lateinit var rellenoAdapter: MultipleSelectRellenoAdapter
    private val rellenoViewModel: RellenoViewModel by viewModels()
    private val listaRelleno = mutableListOf<Relleno>()
    private var listaNombreRelleno = mutableListOf<String>()
    private var relleno: Relleno? = null

    private val productoCubiertaViewModel: ProductoCubiertaViewModel by viewModels()
    private val productoAlturaViewModel: ProductoAlturaViewModel by viewModels()
    private val productoSaborViewModel: ProductoSaborViewModel by viewModels()
    private val productoDiametroViewModel: ProductoDiametroViewModel by viewModels()
    private val productoRellenoViewModel: ProductoRellenoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        InitFirebase()
        fillRecyclerViewReceta(binding.rvRecetaNewproduct)
        fillRecyclerViewSabor(binding.rvSaborNewproduct)
        fillRecyclerViewDiametro(binding.rvDiametroNewproduct)
        fillRecyclerViewRelleno(binding.rvRellenoNewproduct)
        getListaCategoria()
        getListaReceta()
        getListaCubierta()
        getListaAltura()
        getListaSabor()
        getListaDiametro()
        getListaRelleno()
        searchVisibilidad()
        openGaleria()
        listener()
    }

    private fun initViewModel() {
        //VISIBILIDAD
        visibilidadViewModel.visibilidadLiveData.observe( this) {
            if (it != null) {
                visibilidad = it
            }
        }
        //CATEGORIA
        categoriaViewModel.listaNombreCategoria.observe(this) {
            if (it != null) {
                listaNombreCategoria.addAll(it)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCategoriaNewproduct.adapter = adapter
                binding.spCategoriaNewproduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition(it[position])
                        nombreCategoria = it[int]
                        searchCategoria(nombreCategoria)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }
        categoriaViewModel.categoriaLiveDate.observe(this) {
            if (it != null) {
                categoria = it
                showCaracteristicasByCategoria(nombreCategoria)
            }
        }
        //RECETA
        recetaViewModel.listaRecetaLiveData.observe(this) {
            if (it != null) {
                listaReceta.addAll(it)
                recetaAdapter.notifyDataSetChanged()
            }
        }
        //PRODUCTO
        productoViewModel.productoLiveData.observe(this) {
            if (it != null) {
                producto = it
                addCaracteristicas()
            }
        }
        //CUBIERTA
        cubiertaViewModel.listaNombreCubierta.observe(this) {
            if (it != null) {
                listaNombreCubierta.addAll(it)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCubiertaNewproduct.adapter = adapter
                binding.spCubiertaNewproduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition(it[position])
                        nombreCubierta = it[int]
                        searchCubierta(nombreCubierta!!)
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        }
        cubiertaViewModel.cubiertaLiveData.observe(this) {
            if (it != null) {
                cubierta = it
                var productocubierta = ProductoCubierta(null,cubierta,producto)
                productoCubiertaViewModel.guardarProductoCubierta(productocubierta)
            }
        }
        //ALTURA
        alturaViewModel.listaNombreAltura.observe(this) {
            if (it != null) {
                listaNombreAltura.addAll(it)
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spAlturaNewproduct.adapter = adapter
                binding.spAlturaNewproduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            val int = adapter.getPosition(it[position])
                            nombreAltura = it[int]
                            searchAltura(nombreAltura!!)
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }
                    }
            }
        }
        alturaViewModel.alturaLiveData.observe(this) {
            if (it != null) {
                altura = it
                var productoaltura = ProductoAltura(null,altura,producto)
                productoAlturaViewModel.guardarProductoAltura(productoaltura)
            }
        }
        //SABOR
        saborViewModel.listaSaborLiveData.observe(this) {
            if (it != null) {
                listaSabor.addAll(it)
                saborAdapter.notifyDataSetChanged()
            }
        }
        saborViewModel.saborLiveData.observe(this) {
            if (it != null) {
                sabor = it
                var productosabor = ProductoSabor(null,sabor,producto)
                productoSaborViewModel.guardarProductoSabor(productosabor)
            }
        }
        //DIAMETRO
        diametroViewModel.listaDiametroLiveData.observe(this) {
            if (it != null) {
                listaDiametro.addAll(it)
                diametroAdapter.notifyDataSetChanged()
            }
        }
        diametroViewModel.diametroLiveData.observe(this) {
            if (it != null) {
                diametro = it
                var productodiametro = ProductoDiametro(null,diametro,producto)
                productoDiametroViewModel.guardarProductoDiametro(productodiametro)
            }
        }
        //RELLENO
        rellenoViewModel.listaRellenoLiveData.observe(this) {
            if (it != null) {
                listaRelleno.addAll(it)
                rellenoAdapter.notifyDataSetChanged()
            }
        }
        rellenoViewModel.rellenoLiveData.observe(this) {
            if (it != null) {
                relleno = it
                var productorelleno = ProductoRelleno(null,relleno,producto)
                productoRellenoViewModel.guardarProductoRelleno(productorelleno)
            }
        }
        //PRODUCTO ALTURA
        productoAlturaViewModel.productoAlturaLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.altura?.descripcion.toString())
            }
        }
        //PRODUCTO CUBIERTA
        productoCubiertaViewModel.productoCubiertaLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.cubierta?.nombre.toString())
            }
        }
        //PRODUCTO SABOR
        productoSaborViewModel.productoSaborLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.sabor?.nombre.toString())
            }
        }
        //PRODUCTO DIAMETRO
        productoDiametroViewModel.productoDiametroLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.diametro?.descripcion.toString())
            }
        }
        //PRODUCTO RELLENO
        productoRellenoViewModel.productoRellenoLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.relleno?.descripcion.toString())
            }
        }
    }
    private fun InitFirebase(){
        imgreferences = FirebaseDatabase.getInstance().reference.child("FotoProducto")
        storageReference= FirebaseStorage.getInstance().reference.child("imgComprimido")
    }

    private fun fillRecyclerViewReceta(rv: RecyclerView) {
        recetaAdapter = SelectRecetaAdapter(listaReceta,this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv.adapter = recetaAdapter
    }
    private fun fillRecyclerViewSabor(rv: RecyclerView) {
        saborAdapter = MultipleSelectSaborAdapter(listaSabor,this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = saborAdapter
    }
    private fun fillRecyclerViewDiametro(rv: RecyclerView) {
        diametroAdapter = MultipleSelectDiametroAdapter(listaDiametro,this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = diametroAdapter
    }
    private fun fillRecyclerViewRelleno(rv: RecyclerView) {
        rellenoAdapter = MultipleSelectRellenoAdapter(listaRelleno,this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = rellenoAdapter
    }

    private fun getListaReceta() {
        recetaViewModel.listarReceta()
    }
    private fun getListaCategoria() {
        categoriaViewModel.listarNombreCategoria()
    }
    private fun getListaCubierta() {
        cubiertaViewModel.listarNombreCubierta()
    }
    private fun getListaAltura() {
        alturaViewModel.listarNombreAltura()
    }
    private fun getListaSabor() {
        saborViewModel.listarSabor()
    }
    private fun getListaDiametro() {
        diametroViewModel.listarDiametro()
    }
    private fun getListaRelleno() {
        rellenoViewModel.listarRelleno()
    }

    private fun openGaleria(){
        binding.ivProductimgNewproduct.setOnClickListener {
            binding.tvAbrirgaleriaNewproduct.visibility = View.GONE
            val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
            startActivityForResult( galeria, 1001 )
        }
    }
    private fun encodeImage(bitmap: Bitmap): ByteArray {
        val previewWidth = 200
        val previewHeight = 240
        val resized = Bitmap.createScaledBitmap(
            bitmap, (previewWidth),
            (previewHeight), true
        )
        val stream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        return stream.toByteArray()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( resultCode == RESULT_OK && requestCode == 1001 ){
            try {
                // Inputstream == imguri
                val inputStream = data?.data?.let { contentResolver?.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream( inputStream )
                binding.ivProductimgNewproduct.setImageBitmap( bitmap )
                encodeImage = encodeImage( bitmap )
            }catch ( e : FileNotFoundException){
                e.printStackTrace()
            }
        }
    }

    private fun listener() {
        binding.btnSeleccrionarrecetaNewproduct.setOnClickListener { binding.rvRecetaNewproduct.visibility = View.VISIBLE }
        binding.btnAgregarNewproduct.setOnClickListener {
            addPhoto()
        }
    }

    private fun searchVisibilidad(){
        binding.swVisibilidadNewproduct.setOnCheckedChangeListener { _, isChecked ->
            if ( isChecked ) visibilidadViewModel.GetVisibilidad( true ) else visibilidadViewModel.GetVisibilidad( false )
        }
        if( !binding.swVisibilidadNewproduct.isChecked ){ visibilidadViewModel.GetVisibilidad( false ) }
    }
    private fun searchCategoria(nombre:String){
        categoriaViewModel.buscarPorNombreCategoria(nombre)
    }
    private fun searchCubierta(nombre:String){
        cubiertaViewModel.GetCubiertaByNombre(nombre)
    }
    private fun searchAltura(nombre:String){
        alturaViewModel.buscarPorNombreAltura(nombre)
    }
    private fun searchSabores(){
        for(nombre in listaNombreSabor){
            Handler().postDelayed({
                saborViewModel.BuscarSaborNombre(nombre)
            }, 2000)
        }
    }
    private fun searchDiametros(){
        for(nombre in listaNombreDiametro){
            Handler().postDelayed({
                diametroViewModel.GetDiametroByDescrip(nombre)
            }, 2000)
        }
    }
    private fun searchRellenos(){
        for(nombre in listaNombreRelleno){
            Handler().postDelayed({
                rellenoViewModel.GetRellenoByDescrip(nombre)
            }, 2000)
        }
    }

    private fun showCaracteristicasByCategoria(nombre: String){
        if(nombre == "Tortas"){
            binding.tvAlturaNewproduct.visibility = View.GONE
            binding.spAlturaNewproduct.visibility = View.GONE

            binding.tvCubiertaNewproduct.visibility = View.VISIBLE
            binding.spCubiertaNewproduct.visibility = View.VISIBLE
            binding.tvSaborNewproduct.visibility = View.VISIBLE
            binding.rvSaborNewproduct.visibility = View.VISIBLE
            binding.tvDiametroNewproduct.visibility = View.VISIBLE
            binding.rvDiametroNewproduct.visibility = View.VISIBLE
            binding.tvRellenoNewproduct.visibility = View.VISIBLE
            binding.rvRellenoNewproduct.visibility = View.VISIBLE
        }else if(nombre == "Cupcake"){
            binding.tvAlturaNewproduct.visibility = View.GONE
            binding.spAlturaNewproduct.visibility = View.GONE
            binding.tvDiametroNewproduct.visibility = View.GONE
            binding.rvDiametroNewproduct.visibility = View.GONE

            binding.tvCubiertaNewproduct.visibility = View.VISIBLE
            binding.spCubiertaNewproduct.visibility = View.VISIBLE
            binding.tvSaborNewproduct.visibility = View.VISIBLE
            binding.rvSaborNewproduct.visibility = View.VISIBLE
            binding.tvRellenoNewproduct.visibility = View.VISIBLE
            binding.rvRellenoNewproduct.visibility = View.VISIBLE
        }else if(nombre == "Muñecos"){
            binding.tvSaborNewproduct.visibility = View.GONE
            binding.rvSaborNewproduct.visibility = View.GONE
            binding.tvDiametroNewproduct.visibility = View.GONE
            binding.rvDiametroNewproduct.visibility = View.GONE
            binding.tvRellenoNewproduct.visibility = View.GONE
            binding.rvRellenoNewproduct.visibility = View.GONE

            binding.tvAlturaNewproduct.visibility = View.VISIBLE
            binding.spAlturaNewproduct.visibility = View.VISIBLE
            binding.tvCubiertaNewproduct.visibility = View.VISIBLE
            binding.spCubiertaNewproduct.visibility = View.VISIBLE
        }else if(nombre == "Packs"){
            binding.tvAlturaNewproduct.visibility = View.VISIBLE
            binding.spAlturaNewproduct.visibility = View.VISIBLE
            binding.tvCubiertaNewproduct.visibility = View.VISIBLE
            binding.spCubiertaNewproduct.visibility = View.VISIBLE
            binding.tvCubiertaNewproduct.visibility = View.VISIBLE
            binding.spCubiertaNewproduct.visibility = View.VISIBLE
            binding.tvSaborNewproduct.visibility = View.VISIBLE
            binding.rvSaborNewproduct.visibility = View.VISIBLE
            binding.tvDiametroNewproduct.visibility = View.VISIBLE
            binding.rvDiametroNewproduct.visibility = View.VISIBLE
            binding.tvRellenoNewproduct.visibility = View.VISIBLE
            binding.rvRellenoNewproduct.visibility = View.VISIBLE
        }
    }

    private fun addPhoto(){
        val nombre = binding.etNombreNewproduct.text.toString()
        val descripcion = binding.etDescripcionNewproduct.text.toString()
        val precio = binding.etPrecioNewproduct.text.toString()
        val resultNombre = nombre.replace(" ","")

        val ref = storageReference.child( "$resultNombre.jpg" )
        val uploadTask = encodeImage?.let { ref.putBytes(it) }
        val uriTask = uploadTask?.continueWithTask { p0 ->
            if (!p0.isSuccessful) {
                throw Objects.requireNonNull(p0.exception!!)
            }
            ref.downloadUrl
        }?.addOnCompleteListener {
            val downloadUri = it.result
            val entidad = ProductoResponse( resultNombre, downloadUri.toString(), descripcion, null,precio.toDouble(),null,null )
            imgreferences.push().setValue( entidad )
            showMessage( "Imagen Subida con exito." )
            url = downloadUri.toString();
            addNewProduct()
        }
    }
    private fun addNewProduct() {
        val producto = ProductoResponse(
            binding.etNombreNewproduct.text.toString(),
            url,
            binding.etDescripcionNewproduct.text.toString(),
            categoria,
            binding.etPrecioNewproduct.text.toString().toDouble(),
            receta,
            visibilidad
        )
        productoViewModel.guardarProducto( producto )
    }
    private fun addCaracteristicas() {
        if(categoria?.nombre.toString() == "Tortas"){
            searchCubierta(nombreCubierta)
            searchSabores()
            searchDiametros()
            searchRellenos()
        }else if(categoria?.nombre.toString() == "Cupcake"){
            searchCubierta(nombreCubierta)
            searchSabores()
            searchRellenos()
        }else if (categoria?.nombre.toString() == "Muñecos"){
            searchAltura(nombreAltura)
            searchCubierta(nombreCubierta)
        }else if(categoria?.nombre.toString() == "Packs"){
            searchAltura(nombreAltura)
            searchCubierta(nombreCubierta)
            searchSabores()
            searchDiametros()
            searchRellenos()
        }else{
            showMessage("La categoria ${categoria?.nombre.toString()} no existe")
        }
    }

    override fun onDiametroClick(nombre: String, position: Int, list: MutableList<String>) {
        listaNombreDiametro = list
    }
    override fun onRellenoClick(nombre: String, position: Int, list: MutableList<String>) {
        listaNombreRelleno = list
    }
    override fun onSaborClick(nombre: String, position: Int, list: MutableList<String>) {
        listaNombreSabor = list
    }
    override fun onRecetaClick(r: Receta) {
        receta = r
        binding.rvRecetaNewproduct.visibility = View.GONE
        binding.etRecetaNewproduct.setText(receta!!.descripcion.toString())
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }
}