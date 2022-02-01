package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import mood.honeyprojects.gusgusapp.databinding.ActivityEditNoticiaBinding
import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaResponse
import mood.honeyprojects.gusgusapp.viewModel.NoticiaViewModel
import mood.honeyprojects.gusgusapp.viewModel.VisibilidadViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaUpdate
import java.util.*

class EditNoticiaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditNoticiaBinding
    private val noticiaViewModel: NoticiaViewModel by viewModels()
    private val visibilidadViewModel: VisibilidadViewModel by viewModels()

    private lateinit var imgreferences: DatabaseReference
    private lateinit var storageReference: StorageReference

    private var encodeImage: ByteArray?=null
    private var noticias: Noticias? = null
    private var visibilidad: Visibilidad?=null
    private var idnoticiafireb: String? = null
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoticiaBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        InitFirebase()
        FindNoticia()
        ShowDataActualNoticia()
        ShowDataEditNoticia()
        GetDataFotoIDFirebase()
        BuscarVisibilidad()
        Listener()
    }
    private fun Listener(){
        binding.btnEditarEditnoticia.setOnClickListener {
            binding.cvShowEditnoticia.visibility = View.GONE
            binding.btnSubirimgEditnoticia.visibility = View.VISIBLE
            binding.cvEditEditnoticia.visibility = View.VISIBLE
        }
        binding.ivImageEditnoticia.setOnClickListener { AbrirGaleria() }
        binding.btnSubirimgEditnoticia.setOnClickListener {
            if( validateData() ){
                SubirFotoIntoStorage()
            }
        }
        binding.btnGuardarEditnoticia.setOnClickListener {
        if( validarFoto() ){
            GuardarNuevaNoticia()
        }
        }
    }
    private fun InitFirebase(){
        imgreferences = FirebaseDatabase.getInstance().getReference()
        storageReference= FirebaseStorage.getInstance().reference.child("imgComprimido")
    }
    private fun InitViewModel(){
        visibilidadViewModel.visibilidadLiveData.observe( this, Observer {
            if( it != null ){
                visibilidad = it
            }
        } )
        noticiaViewModel.messageResponse.observe( this, Observer {
            if( it != null ){
                ShowMessage( it )
            }
        } )
    }
    private fun BuscarVisibilidad(){
        binding.swtVisibilidadEditnoticia.setOnCheckedChangeListener { _, isChecked ->
            if ( isChecked ) visibilidadViewModel.GetVisibilidad( true ) else visibilidadViewModel.GetVisibilidad( false )
        }
        if( !binding.swtVisibilidadEditnoticia.isChecked ){
            visibilidadViewModel.GetVisibilidad( false )
        }
    }

    private fun GuardarNuevaNoticia(){
        if( url.isEmpty() ){
            val noticia = NoticiaUpdate(
                noticias?.id,
                binding.etNombreEditnoticia.text.toString(),
                noticias?.imgurl,
                binding.etDescripcionEditnoticia.text.toString(),
                visibilidad
            )
            val referenceDatabase =  imgreferences.child( "FotoProducto" ).child( idnoticiafireb!! )
            val nombre = binding.etNombreEditnoticia.text.toString()
            val descripcion = binding.etDescripcionEditnoticia.text.toString()
            val resultNombre = nombre.replace(" ","")

            referenceDatabase.child("imgurl").setValue( noticias?.imgurl )
            referenceDatabase.child( "nombre" ).setValue( resultNombre )
            referenceDatabase.child( "observacion" ).setValue( descripcion )

            noticiaViewModel.ActualizarNoticia( noticia )

            val intent = Intent( this, NoticiaActivity::class.java )
            startActivity( intent )
            finish()
        }else{
            val noticia = NoticiaUpdate(
                noticias?.id,
                binding.etNombreEditnoticia.text.toString(),
                url,
                binding.etDescripcionEditnoticia.text.toString(),
                visibilidad
            )

            noticiaViewModel.ActualizarNoticia( noticia )
            BorrarFotoActualStorage()
            val intent = Intent( this, NoticiaActivity::class.java )
            startActivity( intent )
            finish()
        }
    }
    private fun FindNoticia(){
        val noticia = intent.getSerializableExtra("Noticia") as Noticias
        noticias = noticia
    }
    private fun ShowDataActualNoticia(){
        Picasso.get().load( noticias?.imgurl ).into( binding.ivImageEditnoticia )
        if( noticias?.visibilidad?.visible == true ){
            binding.tvVisibilidadEditnoticia.text = "Visible"
        }else{
            binding.tvVisibilidadEditnoticia.text = "No visible"
        }
        binding.tvNombreEditnoticia.text = noticias?.nombre
        binding.tvDescripcionEditnoticia.text = noticias?.observacion
    }
    private fun ShowDataEditNoticia(){
        binding.etNombreEditnoticia.setText( noticias?.nombre )
        binding.etDescripcionEditnoticia.setText( noticias?.observacion )
        binding.swtVisibilidadEditnoticia.isChecked = noticias?.visibilidad?.visible == true
        visibilidadViewModel.GetVisibilidad( noticias?.visibilidad?.visible!! )
    }

    private fun encodeImage(bitmap: Bitmap): ByteArray {
        val previewWidth = 640
        val previewHeight = 480
        val resized = Bitmap.createScaledBitmap(
            bitmap, (previewWidth),
            (previewHeight), true
        )
        val stream = ByteArrayOutputStream()
        resized.compress(Bitmap.CompressFormat.JPEG, 90, stream)

        return stream.toByteArray()
    }
    private fun AbrirGaleria(){
        binding.ivImageEditnoticia.setOnClickListener {
            val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
            startActivityForResult( galeria, 1001 )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( resultCode == AppCompatActivity.RESULT_OK && requestCode == 1001 ){
            try {
                val inputStream = data?.data?.let { contentResolver?.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream( inputStream )
                binding.ivImageEditnoticia.setImageBitmap( bitmap )
                encodeImage = encodeImage( bitmap )
            }catch ( e : FileNotFoundException){
                e.printStackTrace()
            }
        }
    }

    private fun GetDataFotoIDFirebase(){
        imgreferences.child( "FotoProducto" ).addValueEventListener( object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for( foto in snapshot.children ){
                    val entidad = foto.getValue( NoticiaResponse::class.java )
                    if( entidad?.imgurl == noticias?.imgurl ){
                        idnoticiafireb = foto.key.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        } )
    }

    private fun SubirFotoIntoStorage(){
        val nombre = binding.etNombreEditnoticia.text.toString()
        val descripcion = binding.etDescripcionEditnoticia.text.toString()
        val resultNombre = nombre.replace(" ","")

        val ref = storageReference.child( "$resultNombre.jpg" )
        val referenceDatabase =  imgreferences.child( "FotoProducto" ).child( idnoticiafireb!! )

        val uploadTask = encodeImage?.let { ref.putBytes(it) }
        val uriTask = uploadTask?.continueWithTask { p0 ->
            if (!p0.isSuccessful) {
                throw Objects.requireNonNull(p0.exception!!)
            }
            ref.downloadUrl
        }?.addOnCompleteListener {
            val downloadUri = it.result
            //val entidad = NoticiaResponse( resultNombre, downloadUri.toString(), descripcion, null )
            //referenceDatabase.push().setValue( entidad )
            referenceDatabase.child("imgurl").setValue( downloadUri.toString() )
            referenceDatabase.child( "nombre" ).setValue( resultNombre )
            referenceDatabase.child( "observacion" ).setValue( descripcion )

            ShowMessage( "Imagen Subida con exito." )
            url = downloadUri.toString()
            ShowMessage( url )
        }
    }
    private fun BorrarFotoActualStorage(){
        val nombre = noticias?.nombre
        val resultNombre = nombre?.replace(" ","")
        val ref = storageReference.child( "$resultNombre.jpg" )
        ref.delete().addOnSuccessListener {
            ShowMessage( "Actualizado en el storage" )
        }.addOnFailureListener {
            ShowMessage( "Ocurrió un error en el Storage." )
        }
    }
    private fun validateData(): Boolean{
        if( encodeImage == null ){
            ShowMessage( "Seleccione una foto, la imagen ya está subida." )
            return false
        }else if( binding.etNombreEditnoticia.text.toString().isEmpty() ){
            ShowMessage( "Ingrese un nombre." )
            return false
        }else if( binding.etDescripcionEditnoticia.text.toString().isEmpty() ){
            ShowMessage( "Ingrese una descripción." )
            return false
        }else{
            return true
        }
    }
    private fun validarFoto(): Boolean{
        if( url.isEmpty() && encodeImage != null ){
            ShowMessage( "Primero Suba la nueva imagen." )
            return false
        }else{
            return true
        }
    }
    private fun ShowMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }
}