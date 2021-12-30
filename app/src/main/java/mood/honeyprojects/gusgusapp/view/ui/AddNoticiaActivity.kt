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
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import mood.honeyprojects.gusgusapp.databinding.ActivityAddNoticiaBinding
import mood.honeyprojects.gusgusapp.model.requestEntity.NoticiaResponse
import mood.honeyprojects.gusgusapp.viewModel.NoticiaViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.util.*

class AddNoticiaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoticiaBinding
    private val noticiaViewModel: NoticiaViewModel by viewModels()
    private lateinit var imgreferences: DatabaseReference
    private lateinit var storageReference: StorageReference

    private var encodeImage: ByteArray?=null
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoticiaBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        InitViewModel()
        InitFirebase()
        AbrirGaleria()
        Listener()
    }

    private fun Listener(){
        binding.btnGuardarAddnoticia.setOnClickListener { GuardarNoticia() }
        binding.btnSubirimgAddnoticia.setOnClickListener{ GuardarImagenIntoStorage() }
    }
    private fun InitFirebase(){
        imgreferences = FirebaseDatabase.getInstance().reference.child("FotoProducto")
        storageReference= FirebaseStorage.getInstance().reference.child("imgComprimido")
    }
    private fun InitViewModel(){
        noticiaViewModel.noticiaLiveData.observe( this, Observer {
            if( it != null ){
                Toast.makeText( this, "Noticia Guardada.", Toast.LENGTH_SHORT ).show()
            }
        } )
        noticiaViewModel.messageResponse.observe( this, Observer {
            if( it != null ){
                ShowMessage( it )
            }
        } )
    }
    private fun GuardarNoticia(){
        if( url.isEmpty() ){
            ShowMessage( "Primero suba una foto porfavor." )
        }else{
            ShowMessage( url )
            val noticia = NoticiaResponse(
                binding.etNombreAddnoticia.text.toString(),
                url,
                binding.etDescripcionAddnoticia.text.toString(),
                null
            )
            noticiaViewModel.GuardarNoticia( noticia )
            val intent = Intent( this, NoticiaActivity::class.java )
            startActivity( intent )
            finish()
        }
    }
    private fun AbrirGaleria(){
        binding.ivImageAddnoticia.setOnClickListener {
            val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
            startActivityForResult( galeria, 1001 )
        }
    }
    private fun GuardarImagenIntoStorage(){
        if( binding.etNombreAddnoticia.text.toString().isEmpty() && binding.etDescripcionAddnoticia.text.toString().isEmpty() ){
            ShowMessage( "Ingrese un nombre y descripción de la foto." )
        }else {
            val nombre = binding.etNombreAddnoticia.text.toString()
            val descripcion = binding.etDescripcionAddnoticia.text.toString()
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
                val entidad = NoticiaResponse( resultNombre, downloadUri.toString(), descripcion, null )
                imgreferences.push().setValue( entidad )

                ShowMessage( "Imagen Subida con exito." )
                url = downloadUri.toString();
            }
        }
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( resultCode == AppCompatActivity.RESULT_OK && requestCode == 1001 ){
            try {
                val inputStream = data?.data?.let { contentResolver?.openInputStream(it) }
                val bitmap = BitmapFactory.decodeStream( inputStream )
                binding.ivImageAddnoticia.setImageBitmap( bitmap )
                encodeImage = encodeImage( bitmap )
            }catch ( e : FileNotFoundException){
                e.printStackTrace()
            }
        }
    }
    private fun ShowMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }
}