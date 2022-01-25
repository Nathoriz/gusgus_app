package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityMantCategoriaBinding
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.CategoriaUpdate
import mood.honeyprojects.gusgusapp.view.adapter.MantCategoriaAdapter
import mood.honeyprojects.gusgusapp.viewModel.CategoriaViewModel
import mood.honeyprojects.gusgusapp.viewModel.VisibilidadViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class MantCategoriaActivity : AppCompatActivity(), MantCategoriaAdapter.OnClickCategoriaListener {
    private lateinit var binding: ActivityMantCategoriaBinding
    private lateinit var adapter: MantCategoriaAdapter
    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private val visibilidadViewModel: VisibilidadViewModel by viewModels()
    private val listaCategoria = mutableListOf<Categoria>()
    private var visibilidad: Visibilidad?=null

    private lateinit var imgreferencesSave: DatabaseReference
    private lateinit var imgreferencesUpdate: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var storageReferenceDelete: StorageReference

    private var idCategoriafireb: String? = null
    private var encodeImage: ByteArray?=null
    private var url = ""
    private var oldurl=""
    private var oldimgnombre=""
    private var imgnombre=""

    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0
    private var mostrar:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        searchVisibilidad()
        initFirebase()
        fillRecyclerView( binding.rvCategoriaMantcategoria )
        getListaCategorias()
        listener()
    }

    private fun initViewModel() {
        //CATEGORIA
        categoriaViewModel.listaCategoriaLiveData.observe( this, Observer {
            if( it != null ){
                listaCategoria.clear()
                listaCategoria.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
        categoriaViewModel.categoriaLiveDate.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    clear()
                    listaCategoria.add(it)
                    adapter.notifyItemInserted(listaCategoria.size-1)
                    showMessage("Categoria agregada")
                    accion=""
                }else{
                    mostrar = it.visibilidad?.visible!!

                    url = it.urlimg
                    oldurl = it.urlimg
                    oldimgnombre = it.nombre
                    imgnombre = it.imgnombre

                    Picasso.get().load( oldurl ).into( binding.ivImgMantcategoria )
                    binding.etNombreMantcategoria.setText(it.nombre)
                    binding.swtVisibilidadMantcategoria.setOnCheckedChangeListener(null)
                    binding.swtVisibilidadMantcategoria.isChecked = it.visibilidad?.visible!!
                    searchVisibilidad()
                }
            }
        })
        categoriaViewModel.messageResponse.observe( this, Observer {
            if( it != null ){
                showMessage(it)
            }
        } )
        //VISIBILIDAD
        visibilidadViewModel.visibilidadLiveData.observe( this, Observer {
            if( it != null ){
                visibilidad = it
            }
        } )
    }
    private fun initFirebase(){
        imgreferencesUpdate = FirebaseDatabase.getInstance().getReference()
        imgreferencesSave = FirebaseDatabase.getInstance().reference.child("FotoCategoria")
        storageReference= FirebaseStorage.getInstance().reference.child("imgComprimido")
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = MantCategoriaAdapter( listaCategoria,this )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }
    private fun getListaCategorias() {
        categoriaViewModel.listarCategoria()
    }
    private fun listener() {
        binding.ivImgMantcategoria.setOnClickListener{openGaleria()}
        binding.ivUploadMantcategoria.setOnClickListener{
            if(binding.etNombreMantcategoria.text.toString() == "") {
                showMessage("Ingrese un nombre primero")
            }else{
                saveImageIntoStorage()
            }
        }
        binding.ibAddMantcategoria.setOnClickListener{
            if(url.isEmpty()){
                showMessage("Ups, parece que se le olvido algo\n * susurro * la imagen")
            }else{
//                searchVisibilidad()
                addCategoria()
            }
        }
        binding.ibUpdateMantcategoria.setOnClickListener{
            if(url.isEmpty()){
                showMessage("Ups, parece que se le olvido algo\n * susurro * la imagen")
            }else{
//                searchVisibilidad()
                updateCategoria()
            }
        }
        binding.ibDeleteMantcategoria.setOnClickListener{
            deleteFotoStorage()
            deleteCategoria()}
    }
    private fun searchCategoria(){
        categoriaViewModel.buscarCategoria(idObtenida)
    }
    private fun searchVisibilidad(){
        binding.swtVisibilidadMantcategoria.setOnCheckedChangeListener { _, isChecked ->
            if ( isChecked ) {
                visibilidadViewModel.GetVisibilidad(true)
                showMessage("true")
            } else {
                visibilidadViewModel.GetVisibilidad(false)
                showMessage("false")
            }
        }
        if( !binding.swtVisibilidadMantcategoria.isChecked ) {
            visibilidadViewModel.GetVisibilidad(false)
            showMessage(binding.swtVisibilidadMantcategoria.isChecked.toString())
        }
        showMessage(binding.swtVisibilidadMantcategoria.isChecked.toString())
    }
    private fun openGaleria() {
        val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( galeria, 1001 )
    }

    private fun saveImageIntoStorage(){
        val nombre = binding.etNombreMantcategoria.text.toString()

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
    private fun saveImageIntoStorageUpdate(){
        val datetime = currentDateTime()
        val nombre = binding.etNombreMantcategoria.text.toString()
        val resultNombre = nombre.replace(" ","")
        imgnombre = "$datetime$resultNombre.png"

        val ref = storageReference.child( imgnombre )
        val referenceDatabase = imgreferencesUpdate.child("FotoCategoria").child(idCategoriafireb!!)

        val uploadTask = encodeImage?.let { ref.putBytes(it) }
        val uriTask = uploadTask?.continueWithTask { p0 ->
            if (!p0.isSuccessful) {
                throw Objects.requireNonNull(p0.exception!!)
            }
            ref.downloadUrl
        }?.addOnCompleteListener {
            val downloadUri = it.result
            referenceDatabase.child("urlimg").setValue( downloadUri.toString() )
            referenceDatabase.child( "imgnombre" ).setValue( imgnombre )
            referenceDatabase.child( "nombre" ).setValue( nombre )
            showMessage( "Imagen Subida con exito." )
            url = downloadUri.toString()
            showMessage( url )
        }
    }
    private fun encodeImage(bitmap: Bitmap): ByteArray {
        val previewWidth = 100
        val previewHeight = 100
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
                binding.ivImgMantcategoria.setImageBitmap( bitmap )
                encodeImage = encodeImage( bitmap )
            }catch ( e : FileNotFoundException){
                e.printStackTrace()
            }
        }
    }
    private fun getDataFotoIDFirebase(){
        imgreferencesUpdate.child( "FotoCategoria" ).addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for( foto in snapshot.children ){
                    val entidad = foto.getValue( CategoriaResponse::class.java )
                    if( entidad?.urlimg == oldurl ){
                        idCategoriafireb = foto.key.toString()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        } )
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

    private fun addCategoria() {
        accion = "añadio"
        val categoriaResponse = CategoriaResponse(
            binding.etNombreMantcategoria.text.toString(),
            imgnombre,
            url,
            visibilidad
        )
        categoriaViewModel.guardarCategoria(categoriaResponse)
    }
    private fun updateCategoria() {
        accion = "actualizo"
        val categoria = Categoria(
            idObtenida,
            binding.etNombreMantcategoria.text.toString(),
            imgnombre,
            url,
            visibilidad
        )
        val categoriaUpdate = CategoriaUpdate(
            idObtenida,
            binding.etNombreMantcategoria.text.toString(),
            imgnombre,
            url,
            visibilidad
        )

//        if(oldurl != url){
//            deleteFotoStorage()
//        }else{
//            val referenceDatabase =  imgreferencesUpdate.child( "FotoCategoria" ).child( idnoticiafireb!! )
//            val nombre = binding.etNombreMantcategoria.text.toString()
//            val resultNombre = nombre.replace(" ","")
//
//            referenceDatabase.child("urlimg").setValue( url )
//            referenceDatabase.child( "imgnombre" ).setValue( "${currentDateTime()}$resultNombre.png" )
//            referenceDatabase.child( "nombre" ).setValue( nombre )
//        }

        if(oldurl == url){
            categoriaViewModel.actualizarCategoria(categoriaUpdate)
            listaCategoria[position] = categoria
            adapter.notifyItemChanged(position)
            clear()
            showMessage("Categoria actualizada")
        }else{
            deleteFotoStorage()
            categoriaViewModel.actualizarCategoria(categoriaUpdate)
            listaCategoria[position] = categoria
            adapter.notifyItemChanged(position)
            clear()
            showMessage("Categoria actualizada")
        }

//        categoriaViewModel.actualizarCategoria(categoriaUpdate)
//        listaCategoria[position] = categoria
//        adapter.notifyItemChanged(position)
//        clear()
//        showMessage("Categoria actualizada")
    }
    private fun deleteCategoria() {
        categoriaViewModel.eliminarCategoria(idObtenida)
        listaCategoria.removeAt(position)
        adapter.notifyItemRemoved(position)
        clear()
        showMessage("Categoria eliminada")
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    private fun clear(){
        binding.etNombreMantcategoria.setText("")
        binding.ivImgMantcategoria.setImageResource( R.drawable.ic_image_notfound )
        binding.swtVisibilidadMantcategoria.isChecked = false
    }

    private fun currentDateTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy-hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    override fun onCategoriaClick(id: Long, p: Int) {
        idObtenida = id
        position = p
        searchCategoria()
//        getDataFotoIDFirebase()
    }
}