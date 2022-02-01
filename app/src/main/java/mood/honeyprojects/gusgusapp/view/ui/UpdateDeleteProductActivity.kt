package mood.honeyprojects.gusgusapp.view.ui

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ActivityUpdateDeleteProductBinding
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.model.requestEntity.*
import mood.honeyprojects.gusgusapp.view.adapter.*
import mood.honeyprojects.gusgusapp.viewModel.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

class UpdateDeleteProductActivity : AppCompatActivity(), SelectRecetaAdapter.OnClickRecetaListener,MultipleSelectSaborAdapter.OnClickSaborListener,MultipleSelectDiametroAdapter.OnClickDiametroListener,MultipleSelectRellenoAdapter.OnClickRellenoListener{
    private lateinit var binding: ActivityUpdateDeleteProductBinding

    private lateinit var imgreferences: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var imgreferencesSave: DatabaseReference
    private lateinit var storageReferenceDelete: StorageReference
    private var encodeImage: ByteArray?=null
    private var url = ""
    private var oldurl = ""
    private var idproductofireb: String? = null

    private val productoViewModel: ProductoViewModel by viewModels()
    private var producto: Producto? = null
    private var IDproducto: Long = 0L

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

    // MOSTRAR

    private lateinit var productoCubiertaAdapter: ProductoCubiertaAdapter
    private val productoCubiertaViewModel: ProductoCubiertaViewModel by viewModels()
    private val listaProductoCubierta = mutableListOf<ProductoCubierta>()

    private lateinit var productoAlturaAdapter: ProductoAlturaAdapter
    private val productoAlturaViewModel: ProductoAlturaViewModel by viewModels()
    private val listaProductoAltura = mutableListOf<ProductoAltura>()

    private lateinit var productoSaborAdapter: ProductoSaborAdapter
    private val productoSaborViewModel: ProductoSaborViewModel by viewModels()
    private val listaProductoSabor = mutableListOf<ProductoSabor>()

    private lateinit var productoDiametroAdapter: ProductoDiametroAdapter
    private val productoDiametroViewModel: ProductoDiametroViewModel by viewModels()
    private val listaProductoDiametro = mutableListOf<ProductoDiametro>()

    private lateinit var productoRellenoAdapter: ProductoRellenoAdapter
    private val productoRellenoViewModel: ProductoRellenoViewModel by viewModels()
    private val listaProductoRelleno = mutableListOf<ProductoRelleno>()

    //UPDATE

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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeleteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        IDproducto = intent.getLongExtra("id",0L)

        initViewModel()
        InitFirebase()
        searchProducto()
        fillRecyclerViewProductoSabor(binding.rvProductosaborUpdatedeleteproduct)
        fillRecyclerViewProductoDiametro(binding.rvProductodiametroUpdatedeleteproduct)
        fillRecyclerViewProductoAltura(binding.rvProductoalturaUpdatedeleteproduct)
        fillRecyclerViewProductoCubierta(binding.rvProductocubiertaUpdatedeleteproduct)
        fillRecyclerViewProductoRelleno(binding.rvProductorellenoUpdatedeleteproduct)
        fillRecyclerViewReceta(binding.rvRecetaUpdatedeleteproduct)
        fillRecyclerViewSabor(binding.rvSaborUpdatedeleteproduct)
        fillRecyclerViewDiametro(binding.rvDiametroUpdatedeleteproduct)
        fillRecyclerViewRelleno(binding.rvRellenoUpdatedeleteproduct)
        getListaCategoria()
        getListaReceta()
        getListaCubierta()
        getListaAltura()
        getListaSabor()
        getListaDiametro()
        getListaRelleno()
        getListaProductoCubierta()
        getListaProductoAltura()
        getListaProductoSabor()
        getListaProductoDiametro()
        getListaProductoRelleno()
//        GetDataFotoIDFirebase()
        listener()
    }

    private fun listener() {
        binding.ibUpdateUpdatedeleteproduct.setOnClickListener {
            searchProducto()
            binding.clShowproductoUpdatedeleteproduct.visibility = View.GONE
            binding.ibUpdateUpdatedeleteproduct.visibility=View.GONE
            binding.ibShowUpdatedeleteproduct.visibility = View.VISIBLE
            binding.clUpdateUpdatedeleteproduct.visibility=View.VISIBLE
        }
        binding.ibShowUpdatedeleteproduct.setOnClickListener {
            searchProducto()
            binding.ibShowUpdatedeleteproduct.visibility = View.GONE
            binding.clUpdateUpdatedeleteproduct.visibility=View.GONE

            binding.clShowproductoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.ibUpdateUpdatedeleteproduct.visibility=View.VISIBLE
        }
        binding.ivImgUpdatedeleteproduct.setOnClickListener {
            val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
            startActivityForResult( galeria, 1001 )
        }
        binding.ibUploadUpdatedeleteproduct.setOnClickListener {
            SubirFotoIntoStorage()
        }
        binding.btnMostrarrecetaUpdatedeleteproduct.setOnClickListener {
            binding.rvRecetaUpdatedeleteproduct.visibility = View.VISIBLE
        }
        binding.btnGuardarUpdatedeleteproduct.setOnClickListener {
            updateProducto()
        }
        binding.cvBackUpdatedeleteproduct.setOnClickListener {
            val intent = Intent( this, AllProductsActivity::class.java )
            startActivity( intent )
            finish()
        }
    }

    private fun searchProducto() {
        productoViewModel.buscarProducto(IDproducto)
    }

    private fun getListaProductoRelleno() {
        productoRellenoViewModel.listarProductoRelleno(IDproducto)
    }
    private fun getListaProductoDiametro() {
        productoDiametroViewModel.listarProductoDiametro(IDproducto)
    }
    private fun getListaProductoSabor() {
        productoSaborViewModel.listarProductoSabor(IDproducto)
    }
    private fun getListaProductoAltura() {
        productoAlturaViewModel.listarProductoAltura(IDproducto)
    }
    private fun getListaProductoCubierta() {
        productoCubiertaViewModel.listarProductoCubierta(IDproducto)
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

    private fun fillRecyclerViewProductoRelleno(rv: RecyclerView) {
        productoRellenoAdapter = ProductoRellenoAdapter(listaProductoRelleno)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = productoRellenoAdapter
    }
    private fun fillRecyclerViewProductoCubierta(rv: RecyclerView) {
        productoCubiertaAdapter = ProductoCubiertaAdapter(listaProductoCubierta)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = productoCubiertaAdapter
    }
    private fun fillRecyclerViewProductoAltura(rv: RecyclerView) {
        productoAlturaAdapter = ProductoAlturaAdapter(listaProductoAltura)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = productoAlturaAdapter
    }
    private fun fillRecyclerViewProductoDiametro(rv: RecyclerView) {
        productoDiametroAdapter = ProductoDiametroAdapter(listaProductoDiametro)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = productoDiametroAdapter
    }
    private fun fillRecyclerViewProductoSabor(rv: RecyclerView) {
        productoSaborAdapter = ProductoSaborAdapter(listaProductoSabor)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv.adapter = productoSaborAdapter
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
                val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCategoriaUpdatedeleteproduct.adapter = adapter
                binding.spCategoriaUpdatedeleteproduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
//                showCaracteristicasByCategoria(nombreCategoria)
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
                url = producto?.urlimg.toString()
                oldurl = producto?.urlimg.toString()
                categoria = producto?.categoria
                visibilidad = producto?.visibilidad
                receta = producto?.receta
                binding.swVisibilidadUpdatedeleteproduct.setOnCheckedChangeListener(null)
                binding.swVisibilidadUpdatedeleteproduct.isChecked = it.visibilidad?.visible!!
                showDetalleProducto()
                showCaracteristicasByCategoria(producto?.categoria?.nombre.toString())
                searchVisibilidad()
            }
        }
        //CUBIERTA
        cubiertaViewModel.listaNombreCubierta.observe(this) {
            if (it != null) {
                listaNombreCubierta.addAll(it)
                val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCubiertaUpdatedeleteproduct.adapter = adapter
                binding.spCubiertaUpdatedeleteproduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
//                var productocubierta = ProductoCubierta(null,cubierta,producto)
//                productoCubiertaViewModel.guardarProductoCubierta(productocubierta)
            }
        }
        //ALTURA
        alturaViewModel.listaNombreAltura.observe(this) {
            if (it != null) {
                listaNombreAltura.addAll(it)
                val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, it)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spAlturaUpdatedeleteproduct.adapter = adapter
                binding.spAlturaUpdatedeleteproduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
//                var productoaltura = ProductoAltura(null,altura,producto)
//                productoAlturaViewModel.guardarProductoAltura(productoaltura)
            }
        }
        //SABOR
        saborViewModel.listaSaborLiveData.observe(this) {
            if (it != null) {
                listaSabor.clear()
                listaSabor.addAll(it)
                saborAdapter.notifyDataSetChanged()
            }
        }
        saborViewModel.saborLiveData.observe(this) {
            if (it != null) {
                sabor = it
//                var productosabor = ProductoSabor(null,sabor,producto)
//                productoSaborViewModel.guardarProductoSabor(productosabor)
            }
        }
        //DIAMETRO
        diametroViewModel.listaDiametroLiveData.observe(this) {
            if (it != null) {
                listaDiametro.clear()
                listaDiametro.addAll(it)
                diametroAdapter.notifyDataSetChanged()
            }
        }
        diametroViewModel.diametroLiveData.observe(this) {
            if (it != null) {
                diametro = it
//                var productodiametro = ProductoDiametro(null,diametro,producto)
//                productoDiametroViewModel.guardarProductoDiametro(productodiametro)
            }
        }
        //RELLENO
        rellenoViewModel.listaRellenoLiveData.observe(this) {
            if (it != null) {
                listaRelleno.clear()
                listaRelleno.addAll(it)
                rellenoAdapter.notifyDataSetChanged()
            }
        }
        rellenoViewModel.rellenoLiveData.observe(this) {
            if (it != null) {
                relleno = it
//                var productorelleno = ProductoRelleno(null,relleno,producto)
//                productoRellenoViewModel.guardarProductoRelleno(productorelleno)
            }
        }
        //PRODUCTO ALTURA
        productoAlturaViewModel.listaProductoAlturaLiveData.observe(this) {
            if (it != null) {
                listaProductoAltura.addAll(it)
                productoRellenoAdapter.notifyDataSetChanged()
            }
        }
        productoAlturaViewModel.productoAlturaLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.altura?.descripcion.toString())
            }
        }
        //PRODUCTO CUBIERTA
        productoCubiertaViewModel.listaProductoCubiertaLiveData.observe(this) {
            if (it != null) {
                listaProductoCubierta.addAll(it)
                productoCubiertaAdapter.notifyDataSetChanged()
            }
        }
        productoCubiertaViewModel.productoCubiertaLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.cubierta?.nombre.toString())
            }
        }
        //PRODUCTO SABOR
        productoSaborViewModel.listaProductoSaborLiveData.observe(this) {
            if (it != null) {
                listaProductoSabor.addAll(it)
                productoSaborAdapter.notifyDataSetChanged()
            }
        }
        productoSaborViewModel.productoSaborLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.sabor?.nombre.toString())
            }
        }
        //PRODUCTO DIAMETRO
        productoDiametroViewModel.listaProductoDiametroLiveData.observe(this) {
            if (it != null) {
                listaProductoDiametro.addAll(it)
                productoDiametroAdapter.notifyDataSetChanged()
            }
        }
        productoDiametroViewModel.productoDiametroLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.diametro?.descripcion.toString())
            }
        }
        //PRODUCTO RELLENO
        productoRellenoViewModel.listaProductoRellenoLiveData.observe(this) {
            if (it != null) {
                listaProductoRelleno.addAll(it)
                productoRellenoAdapter.notifyDataSetChanged()
            }
        }
        productoRellenoViewModel.productoRellenoLiveData.observe(this) {
            if (it != null) {
//                showMessage(it.relleno?.descripcion.toString())
            }
        }
    }

    private fun InitFirebase(){
        imgreferences = FirebaseDatabase.getInstance().getReference()
        imgreferencesSave = FirebaseDatabase.getInstance().reference.child("FotoProducto")
        storageReference= FirebaseStorage.getInstance().reference.child("imgComprimido")
    }

    private fun searchVisibilidad(){
        binding.swVisibilidadUpdatedeleteproduct.setOnCheckedChangeListener { _, isChecked ->
            if ( isChecked ) {
                visibilidadViewModel.GetVisibilidad( true )
                showMessage(true.toString())
            }
            else {
                visibilidadViewModel.GetVisibilidad( false )
                showMessage(false.toString())
            }
        }
        if( !binding.swVisibilidadUpdatedeleteproduct.isChecked ){
            visibilidadViewModel.GetVisibilidad( false )
        }
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
        if( resultCode == AppCompatActivity.RESULT_OK && requestCode == 1001 ){
            try {
                val inputStream = data?.data?.let { contentResolver?.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream( inputStream )
                binding.ivImgUpdatedeleteproduct.setImageBitmap( bitmap )
                encodeImage = encodeImage( bitmap )
            }catch ( e : FileNotFoundException){
                e.printStackTrace()
            }
        }
    }

    private fun SubirFotoIntoStorage(){
        val nombre = binding.etNombreUpdatedeleteproduct.text.toString()
        val resultNombre = nombre.replace(" ","")
//        val datetime = currentDateTime()
//        imgnombre = "$datetime$resultNombre.png"
        val imgnombre = "$resultNombre.png"
        val ref = storageReference.child( imgnombre )

        val uploadTask = encodeImage?.let { ref.putBytes(it) }
        val uriTask = uploadTask?.continueWithTask { p0 ->
            if (!p0.isSuccessful) {
                throw Objects.requireNonNull(p0.exception!!)
            }
            ref.downloadUrl
        }?.addOnCompleteListener {
            val downloadUri = it.result
            val entidad = ProductoResponse(
                resultNombre,
                url,
                binding.etDescripcionUpdatedeleteproduct.text.toString(),
                categoria,
                binding.etPrecioUpdatedeleteproduct.text.toString().toDouble(),
                receta,
                visibilidad
            )
            imgreferencesSave.push().setValue( entidad )
            showMessage( "Imagen Subida con exito." )
            url = downloadUri.toString();
        }
    }
    private fun deleteFotoStorage() {
        storageReferenceDelete= FirebaseStorage.getInstance().getReferenceFromUrl(oldurl)
        val ref = storageReferenceDelete
        ref.delete().addOnSuccessListener {
            showMessage( "Eliminado en el storage" )
        }.addOnFailureListener {
            showMessage( "Ocurrió un error en el Storage al eliminar." )
        }
        clear()
    }

    private fun updateProducto(){
        val producto = ProductoUpdate(
            IDproducto,
            binding.etNombreUpdatedeleteproduct.text.toString(),
            url,
            binding.etDescripcionUpdatedeleteproduct.text.toString(),
            categoria,
            binding.etPrecioUpdatedeleteproduct.text.toString().toDouble(),
            receta,
            visibilidad
        )
        if(oldurl != url){
            deleteFotoStorage()
        }
        productoViewModel.actualizarProducto( producto )
        clear()
//        updateCaracteristicasProducto()
    }

    private fun updateCaracteristicasProducto(){

    }

    private fun showDetalleProducto(){
        Picasso.get().load( producto?.urlimg ).into( binding.ivImgproductUpdatedeleteproduct )
        Picasso.get().load( producto?.urlimg ).into( binding.ivImgUpdatedeleteproduct )
        binding.tvNombreUpdatedeleteproduct.text =  binding.tvNombreUpdatedeleteproduct.text.toString() + producto?.nombre
        binding.etNombreUpdatedeleteproduct.setText(producto?.nombre)
        binding.tvDescripcionUpdatedeleteproduct.text = binding.tvDescripcionUpdatedeleteproduct.text.toString() + producto?.descripcion
        binding.etDescripcionUpdatedeleteproduct.setText(producto?.descripcion)
        binding.tvPrecioUpdatedeleteproduct.text = binding.tvPrecioUpdatedeleteproduct.text.toString() + producto?.precio.toString()
        binding.etPrecioUpdatedeleteproduct.setText(producto?.precio.toString())
        binding.tvCategoriaUpdatedeleteproduct.text =  binding.tvCategoriaUpdatedeleteproduct.text.toString() + producto?.categoria?.nombre
        if(producto?.receta?.descripcion.equals(null)){
            binding.tvNombrerecetaUpdatedeleteproduct.text = "No se asigno una receta"
        }else{
            binding.tvNombrerecetaUpdatedeleteproduct.text = producto?.receta?.descripcion
            binding.etRecetaUpdatedeleteproduct.setText(producto?.receta?.descripcion)
        }
        if(producto?.visibilidad?.visible == true){
            binding.tvVisibilidadUpdatedeleteproduct.text = "PUBLICADO"
        }else{
            binding.tvVisibilidadUpdatedeleteproduct.text = "NO PUBLICADO"
        }
    }
    private fun showCaracteristicasByCategoria(nombre: String){
        if(nombre == "Tortas"){
            binding.tvAlturaUpdatedeleteproduct.visibility = View.GONE
            binding.spAlturaUpdatedeleteproduct.visibility = View.GONE
            binding.tvAlturaupUpdatedeleteproduct.visibility = View.GONE
            binding.tvCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.spCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvCubiertaupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvSaborUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvSaborUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvSaborupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvDiametroUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvDiametroUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvDiametroupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvRellenoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvRellenoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvRellenoupUpdatedeleteproduct.visibility = View.VISIBLE
        }else if(nombre == "Cupcake"){
            binding.tvAlturaUpdatedeleteproduct.visibility = View.GONE
            binding.spAlturaUpdatedeleteproduct.visibility = View.GONE
            binding.tvAlturaupUpdatedeleteproduct.visibility = View.GONE
            binding.tvDiametroUpdatedeleteproduct.visibility = View.GONE
            binding.rvDiametroUpdatedeleteproduct.visibility = View.GONE
            binding.tvDiametroupUpdatedeleteproduct.visibility = View.GONE
            binding.tvCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.spCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvCubiertaupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvSaborUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvSaborUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvSaborupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvRellenoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvRellenoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvRellenoupUpdatedeleteproduct.visibility = View.VISIBLE
        }else if(nombre == "Muñecos"){
            binding.tvDiametroUpdatedeleteproduct.visibility = View.GONE
            binding.rvDiametroUpdatedeleteproduct.visibility = View.GONE
            binding.tvDiametroupUpdatedeleteproduct.visibility = View.GONE
            binding.tvSaborUpdatedeleteproduct.visibility = View.GONE
            binding.rvSaborUpdatedeleteproduct.visibility = View.GONE
            binding.tvSaborupUpdatedeleteproduct.visibility = View.GONE
            binding.tvRellenoUpdatedeleteproduct.visibility = View.GONE
            binding.rvRellenoUpdatedeleteproduct.visibility = View.GONE
            binding.tvRellenoupUpdatedeleteproduct.visibility = View.GONE
            binding.tvAlturaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.spAlturaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvAlturaupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvProductoalturaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.spCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvCubiertaupUpdatedeleteproduct.visibility = View.VISIBLE

        }else if(nombre == "Packs"){
            binding.tvAlturaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.spAlturaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvAlturaupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.spCubiertaUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvCubiertaupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvSaborUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvSaborUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvSaborupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvDiametroUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvDiametroUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvDiametroupUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvRellenoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.rvRellenoUpdatedeleteproduct.visibility = View.VISIBLE
            binding.tvRellenoupUpdatedeleteproduct.visibility = View.VISIBLE
        }
    }
    private fun clear(){
        binding.tvNombreUpdatedeleteproduct.text =  ""
        binding.etNombreUpdatedeleteproduct.setText("")
        binding.tvDescripcionUpdatedeleteproduct.text = ""
        binding.etDescripcionUpdatedeleteproduct.setText("")
        binding.tvPrecioUpdatedeleteproduct.text = ""
        binding.etPrecioUpdatedeleteproduct.setText("")
        binding.tvCategoriaUpdatedeleteproduct.text =  ""
        binding.tvNombrerecetaUpdatedeleteproduct.text = ""
        binding.etRecetaUpdatedeleteproduct.setText("")
        binding.tvVisibilidadUpdatedeleteproduct.text = ""
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
        binding.rvRecetaUpdatedeleteproduct.visibility = View.GONE
        binding.etRecetaUpdatedeleteproduct.setText(receta!!.descripcion.toString())
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }
}