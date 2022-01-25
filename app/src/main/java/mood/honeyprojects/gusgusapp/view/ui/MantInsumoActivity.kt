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
import mood.honeyprojects.gusgusapp.databinding.ActivityMantInsumoBinding
import mood.honeyprojects.gusgusapp.model.entity.Insumo
import mood.honeyprojects.gusgusapp.model.requestEntity.InsumoResponse
import mood.honeyprojects.gusgusapp.model.requestEntity.InsumoUpdate
import mood.honeyprojects.gusgusapp.view.adapter.MantInsumoAdapter
import mood.honeyprojects.gusgusapp.viewModel.InsumoViewModel
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.text.SimpleDateFormat
import java.util.*

class MantInsumoActivity : AppCompatActivity(), MantInsumoAdapter.OnClickInsumoListener {
    private lateinit var binding: ActivityMantInsumoBinding
    private lateinit var adapter: MantInsumoAdapter
    private val insumoViewModel: InsumoViewModel by viewModels()
    private val listaInsumo = mutableListOf<Insumo>()

    private lateinit var imgreferencesSave: DatabaseReference
    private lateinit var imgreferencesUpdate: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var storageReferenceDelete: StorageReference

    private var idinsumofireb: String? = null
    private var encodeImage: ByteArray?=null
    private var url = ""
    private var oldurl=""
    private var imgnombre=""
    private var oldimgnombre=""

    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantInsumoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        initFirebase()
        fillRecyclerView( binding.rvInsumosMantinsumo )
        getListaInsumos()
        listener()
    }

    private fun initViewModel() {
        insumoViewModel.listaInsumoLiveData.observe( this, Observer {
            if( it != null ){
                listaInsumo.clear()
                listaInsumo.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
        insumoViewModel.insumoLiveDate.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    clear()
                    listaInsumo.add(it)
                    adapter.notifyItemInserted(listaInsumo.size-1)
                    showMessage("Insumo agregado")
                    accion=""
                }else{
                    url = it.img.toString()
                    oldurl = it.img.toString()
                    oldimgnombre = it.imgnombre.toString()
                    imgnombre = it.imgnombre.toString()

                    Picasso.get().load( oldurl ).into( binding.ivImgMantinsumo )
                    binding.etNombreMantinsumo.setText(it.nombre)
                }
            }
        })
        insumoViewModel.messageResponse.observe( this, Observer {
            if( it != null ){
                showMessage(it)
            }
        } )
    }
    private fun initFirebase() {
        imgreferencesUpdate = FirebaseDatabase.getInstance().getReference()
        imgreferencesSave = FirebaseDatabase.getInstance().reference.child("FotoInsumo")
        storageReference= FirebaseStorage.getInstance().reference.child("imgComprimido")
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = MantInsumoAdapter( listaInsumo,this )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }
    private fun getListaInsumos() {
        insumoViewModel.listarInsumo()
    }
    private fun listener() {
        binding.ivImgMantinsumo.setOnClickListener{openGaleria()}
        binding.ivUploadMantinsumo.setOnClickListener{
            if(binding.etNombreMantinsumo.text.toString() == "") {
                showMessage("Ingrese un nombre primero")
            }else{
                saveImageIntoStorage()
            }
        }
        binding.ibAddMantinsumo.setOnClickListener{
            if(url.isEmpty()){
                showMessage("Ups, parece que se le olvido algo\n * susurro * la imagen")
            }else{
                addInsumo()
            }
        }
        binding.ibUpdateMantinsumo.setOnClickListener{
            if(url.isEmpty()){
                showMessage("Ups, parece que se le olvido algo\n * susurro * la imagen")
            }else{
                updateInsumo()
            }
        }
        binding.ibDeleteMantinsumo.setOnClickListener{
            deleteFotoStorage()
            deleteInsumo()}
    }

    private fun openGaleria() {
        val galeria = Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI )
        startActivityForResult( galeria, 1001 )
    }
    private fun saveImageIntoStorage() {
        val nombre = binding.etNombreMantinsumo.text.toString()

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
            val entidad = InsumoResponse( resultNombre, imgnombre,downloadUri.toString() )
            imgreferencesSave.push().setValue( entidad )
            showMessage( "Imagen Subida con exito." )
            url = downloadUri.toString();
        }
    }
    private fun getDataFotoIDFirebase(){
        imgreferencesUpdate.child( "FotoCategoria" ).addValueEventListener( object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for( foto in snapshot.children ){
                    val entidad = foto.getValue( InsumoResponse::class.java )
                    if( entidad?.img == oldurl ){
                        idinsumofireb = foto.key.toString()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        } )
    }
    private fun saveImageIntoStorageUpdate(){
        val datetime = currentDateTime()
        val nombre = binding.etNombreMantinsumo.text.toString()
        val resultNombre = nombre.replace(" ","")
        imgnombre = "$datetime$resultNombre.png"

        val ref = storageReference.child( imgnombre )
        val referenceDatabase = imgreferencesUpdate.child("FotoInsumo").child(idinsumofireb!!)

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
                binding.ivImgMantinsumo.setImageBitmap( bitmap )
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

    private fun searchInsumo(){
        insumoViewModel.buscarInsumo(idObtenida)
    }
    private fun addInsumo() {
        accion = "añadio"
        val insumoResponse = InsumoResponse(
            binding.etNombreMantinsumo.text.toString(),
            imgnombre,
            url
        )
        insumoViewModel.guardarInsumo(insumoResponse)
    }
    private fun updateInsumo() {
        accion = "actualizo"
        val insumo = Insumo(
            idObtenida,
            binding.etNombreMantinsumo.text.toString(),
            imgnombre,
            url
        )
        val insumoUpdate = InsumoUpdate(
            idObtenida,
            binding.etNombreMantinsumo.text.toString(),
            imgnombre,
            url
        )

        if(oldurl == url){
            insumoViewModel.actualizarInsumo(insumoUpdate)
            listaInsumo[position] = insumo
            adapter.notifyItemChanged(position)
            clear()
            showMessage("Insumo actualizada")
        }else{
            deleteFotoStorage()
            insumoViewModel.actualizarInsumo(insumoUpdate)
            listaInsumo[position] = insumo
            adapter.notifyItemChanged(position)
            clear()
            showMessage("Insumo actualizada")
        }

//        insumoViewModel.actualizarInsumo(insumoUpdate)
//        listaInsumo[position] = insumo
//        adapter.notifyItemChanged(position)
//        clear()
//        showMessage("Insumo actualizada")
    }
    private fun deleteInsumo() {
        insumoViewModel.eliminarInsumo(idObtenida)
        listaInsumo.removeAt(position)
        adapter.notifyItemRemoved(position)
        clear()
        showMessage("Insumo eliminada")
    }

    private fun currentDateTime(): String {
        val sdf = SimpleDateFormat("dd/M/yyyy-hh:mm:ss")
        val currentDate = sdf.format(Date())
        return currentDate
    }

    private fun clear(){
        binding.etNombreMantinsumo.setText("")
        binding.ivImgMantinsumo.setImageResource( R.drawable.ic_image_notfound )
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onInsumoClick(id: Long, p: Int) {
        idObtenida = id
        position = p
        searchInsumo()
    }
}