package mood.honeyprojects.gusgusapp.view.ui

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import mood.honeyprojects.gusgusapp.databinding.ActivityRegistroProductoBinding
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaResponse
import mood.honeyprojects.gusgusapp.viewModel.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class RegistroProductoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroProductoBinding

    private val productoViewModel: ProductoViewModel by viewModels()
    private val listaProducto = mutableListOf<Producto>()

    private val visibilidadViewModel: VisibilidadViewModel by viewModels()

    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private val listaNombreCategoria= mutableListOf<String>()

    private val saborViewModel: SaborViewModel by viewModels()
    private val listaNombreSabor = mutableListOf<String>()

    private val cubiertaViewModel: CubiertaViewModel by viewModels()
    private val listaNombreCubierta = mutableListOf<String>()

    private val diametroViewModel: DiametroViewModel by viewModels()
    private val listaNombreDiametro = mutableListOf<String>()

    private val alturaViewModel: AlturaViewModel by viewModels()
    private val listaNombreAltura = mutableListOf<String>()

    private val rellenoViewModel: RellenoViewModel by viewModels()
    private val listaNombreRelleno = mutableListOf<String>()

    private val recetaViewModel: RecetaViewModel by viewModels()
    private val listaNombreReceta = mutableListOf<String>()

    private var categoria: Categoria? = null
    private var receta: Receta? = null

    private var producto: Producto?=null
    private var visibilidad: Visibilidad?=null
    private var sabor: Sabor? = null
    private var cubierta: Cubierta? = null
    private var relleno: Relleno? = null
    private var diametro: Diametro? = null
    private var altura: Altura? = null

    private var nombreCategoria:String = ""
    private var nombreReceta:String = ""
    private var nombreSabor:String = ""
    private var nombreCubierta:String = ""
    private var nombreDiametro:String = ""
    private var nombreAltura:String = ""
    private var nombreRelleno:String = ""

    private lateinit var storageReferenceDelete: StorageReference
    private lateinit var imgreferencesSave: DatabaseReference
    private lateinit var storageReference: StorageReference

    private var encodeImage: ByteArray?=null
    private var oldurl = ""
    private var url = ""
    private var imgnombre=""

    private var mostrar:Boolean = false

    private var accion:String = ""
    private var idObtenida:Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        searchVisibilidad()
        initFirebase()
        getListaCategorias()
        getListaRecetas()
        getListaSabores()
        getListaCubierta()
        getListaDiametro()
        getListaAltura()
        listener()
    }

    private fun initViewModel() {
        //VISIBILIDAD
        visibilidadViewModel.visibilidadLiveData.observe( this, {
            if( it != null ){
                visibilidad = it
            }
        } )
        //Categoria
        categoriaViewModel.listaNombreCategoria.observe(this,{
            if(it !=null){
                listaNombreCategoria.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCategoriaRegistroproducto.adapter = adapter

                binding.spCategoriaRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreCategoria = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreCategoria, Toast.LENGTH_LONG ).show()
                        searchCategoria( nombreCategoria!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        categoriaViewModel.categoriaLiveDate.observe(this,{
            if(it!=null){
                categoria = it
            }
        })
        //Receta
        recetaViewModel.listaNombreReceta.observe(this,{
            if(it !=null){
                listaNombreReceta.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spRecetaRegistroproducto.adapter = adapter

                binding.spRecetaRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreReceta = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreReceta, Toast.LENGTH_LONG ).show()
                        searchReceta( nombreReceta!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        recetaViewModel.recetaLiveData.observe(this,{
            if(it!=null){
                receta = it
            }
        })
        //Sabor
        saborViewModel.listaNombreSabor.observe(this,{
            if(it !=null){
                listaNombreSabor.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spSaborRegistroproducto.adapter = adapter

                binding.spSaborRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreSabor = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreSabor, Toast.LENGTH_LONG ).show()
                        searchSabor( nombreSabor!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        saborViewModel.saborLiveData.observe(this,{
            if(it!=null){
                sabor = it
            }
        })
        //Cubierta
        cubiertaViewModel.listaNombreCubierta.observe(this,{
            if(it !=null){
                listaNombreSabor.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spCubiertaRegistroproducto.adapter = adapter

                binding.spCubiertaRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreCubierta = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreCubierta, Toast.LENGTH_LONG ).show()
                        searchCubierta( nombreCubierta!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        cubiertaViewModel.cubiertaLiveData.observe(this,{
            if(it!=null){
                cubierta = it
            }
        })
        //Relleno
        rellenoViewModel.listaDescripcionRelleno.observe(this,{
            if(it !=null){
                listaNombreRelleno.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spRellenoRegistroproducto.adapter = adapter

                binding.spRellenoRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreRelleno = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreRelleno, Toast.LENGTH_LONG ).show()
                        searchRelleno( nombreRelleno!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        rellenoViewModel.rellenoLiveData.observe(this,{
            if(it!=null){
                relleno = it
            }
        })
        //Diametro
        diametroViewModel.listaNombreDiametro.observe(this,{
            if(it !=null){
                listaNombreDiametro.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spDiametroRegistroproducto.adapter = adapter

                binding.spDiametroRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreDiametro = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreDiametro, Toast.LENGTH_LONG ).show()
                        searchDiametro( nombreDiametro!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        diametroViewModel.diametroLiveData.observe(this,{
            if(it!=null){
                diametro = it
            }
        })
        //Altura
        alturaViewModel.listaNombreAltura.observe(this,{
            if(it !=null){
                listaNombreAltura.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spAlturaRegistroproducto.adapter = adapter

                binding.spAlturaRegistroproducto.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreAltura = it[ int ]
                        Toast.makeText( this@RegistroProductoActivity, nombreAltura, Toast.LENGTH_LONG ).show()
                        searchAltura( nombreAltura!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        alturaViewModel.alturaLiveData.observe(this,{
            if(it!=null){
                altura = it
            }
        })
        //Producto
        productoViewModel.productoLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    showMessage("Categoria agregada")
                    accion=""
                }else{
                    mostrar = it.visibilidad?.visible!!
                    url = it.urlimg.toString()
                }
            }
        })
        productoViewModel.responseMensaje.observe( this, {
            if( it != null ){
                showMessage(it)
            }
        } )

    }

    private fun searchVisibilidad() {
        binding.swtVisibilidadRegistroproducto.setOnCheckedChangeListener { _, isChecked ->
            if ( isChecked ) {
                visibilidadViewModel.GetVisibilidad(true)
                showMessage("true")
            } else {
                visibilidadViewModel.GetVisibilidad(false)
                showMessage("false")
            }
        }
        if( !binding.swtVisibilidadRegistroproducto.isChecked ) {
            visibilidadViewModel.GetVisibilidad(false)
            showMessage(binding.swtVisibilidadRegistroproducto.isChecked.toString())
        }
        showMessage(binding.swtVisibilidadRegistroproducto.isChecked.toString())
    }
    private fun searchProducto() {
        productoViewModel.ListForIdProduct(idObtenida)
    }
    private fun searchCategoria(nombre: String) {
        categoriaViewModel.buscarPorNombreCategoria(nombre)
    }
    private fun searchReceta(nombre: String) {
        recetaViewModel.buscarPorNombreReceta(nombre)
    }
    private fun searchSabor(nombre: String) {
        saborViewModel.BuscarSaborNombre(nombre)
    }
    private fun searchCubierta(nombre: String) {
        cubiertaViewModel.GetCubiertaByNombre(nombre)
    }
    private fun searchRelleno(nombre: String) {
        rellenoViewModel.GetRellenoByDescrip(nombre)
    }
    private fun searchDiametro(nombre: String) {
        diametroViewModel.GetDiametroByDescrip(nombre)
    }
    private fun searchAltura(nombre: String) {
        alturaViewModel.buscarPorNombreAltura(nombre)
    }

    private fun initFirebase() {
        TODO("Not yet implemented")
    }
    private fun openGaleria() {
        val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( galeria, 1001 )
    }
    private fun saveImageIntoStorage(){
        val nombre = binding.etNombreRegistroproducto.text.toString()
        val resultNombre = nombre.replace(" ","")
        val datetime = currentDateTime()

        imgnombre = "$datetime$resultNombre.png"
        val ref = storageReference.child( imgnombre )

        val uploadTask = encodeImage?.let { ref.putBytes(it) }
        val uriTask = uploadTask?.continueWithTask { p0 ->
            if (!p0.isSuccessful) {
                throw Objects.requireNonNull(p0.exception!!)
            }
            ref.downloadUrl
        }?.addOnCompleteListener {
            val downloadUri = it.result
            val entidad = CategoriaResponse( resultNombre, imgnombre,downloadUri.toString(), null )
            imgreferencesSave.push().setValue( entidad )
            showMessage( "Imagen Subida con exito." )
            url = downloadUri.toString();
        }
    }
    private fun encodeImage(bitmap: Bitmap): ByteArray {
        val previewWidth = 140
        val previewHeight = 160
        val resized = Bitmap.createScaledBitmap(
            bitmap, (previewWidth),
            (previewHeight), true
        )
        val stream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.PNG, 90, stream)
        return stream.toByteArray()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( resultCode == AppCompatActivity.RESULT_OK && requestCode == 1001 ){
            try {
                val inputStream = data?.data?.let { contentResolver?.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream( inputStream )
                binding.ivImgRegistroproducto.setImageBitmap( bitmap )
                encodeImage = encodeImage( bitmap )
            }catch ( e : FileNotFoundException){
                e.printStackTrace()
            }
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
    }

    private fun getListaCategorias() {
        categoriaViewModel.listarNombreCategoria()
    }
    private fun getListaRecetas() {
        recetaViewModel.listarNombreReceta()
    }
    private fun getListaSabores() {
        saborViewModel.listarNombreSabor()
    }
    private fun getListaCubierta() {
        cubiertaViewModel.listarNombreCubierta()
    }
    private fun getListaDiametro() {
        diametroViewModel.listarNombreDiametro()
    }
    private fun getListaAltura() {
        alturaViewModel.listarNombreAltura()
    }

    private fun listener() {
        binding.ivBackRegistroproducto.setOnClickListener{

        }
        binding.ivImgRegistroproducto.setOnClickListener{

        }
        binding.ibUploadRegistroproducto.setOnClickListener{

        }
        binding.btnRegistroproductoRegistroproducto.setOnClickListener{

        }
        binding.ibAdddiametroRegistroproducto.setOnClickListener{

        }
        binding.ibAaddalturaRegistroproducto.setOnClickListener{

        }
        binding.ibAddrellenoRegistroproducto.setOnClickListener{

        }
        binding.ibAddsaborRegistroproducto.setOnClickListener{

        }
        binding.btnRegistrarextrasRegistroproducto.setOnClickListener{

        }
    }


    private fun addProducto() {
//        accion="añadio"
//        val recetaInsumo = RecetaInsumo(
//            null,
//            receta,
//            insumo,
//            binding.etCantidadMantrecetainsumo.text.toString()
//        )
//        recetaInsumoViewModel.guardarRecetaInsumo(recetaInsumo)
    }

    private fun clear() {
        binding.etNombreRegistroproducto.setText("")
        binding.etDescripcionRegistroproducto.setText("")
        binding.etPrecioRegistroproducto.setText("")
        binding.ivImgRegistroproducto.setImageResource(mood.honeyprojects.gusgusapp.R.drawable.ic_image_notfound)
        binding.swtVisibilidadRegistroproducto.isChecked = false
    }

    private fun currentDateTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy-hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    private fun showMessage(message: String) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}