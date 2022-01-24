package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityMantAlturaBinding
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.view.adapter.AlturaAdapter
import mood.honeyprojects.gusgusapp.viewModel.AlturaViewModel

class MantAlturaActivity : AppCompatActivity(),AlturaAdapter.OnClickAlturaListener {
    private lateinit var binding: ActivityMantAlturaBinding
    private lateinit var adapter: AlturaAdapter
    private val alturaViewModel:AlturaViewModel by viewModels()
    private val listaAltura = mutableListOf<Altura>()
    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantAlturaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvAlturasMantaltura)
        getListaAltura()
        listener()
    }

    private fun initViewModel() {
        alturaViewModel.listaAlturaLiveData.observe(this,{
            if(it !=null){
                binding.rvAlturasMantaltura.visibility = View.VISIBLE
                listaAltura.clear()
                listaAltura.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        alturaViewModel.alturaLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaAltura.add(it)
                    adapter.notifyItemInserted(listaAltura.size-1)
                    showMessage("Altura agregado")
                    accion=""
                }
                binding.etNombreMantaltura.setText(it.descripcion.toString())
                binding.etPrecioMantaltura.setText(it.precio.toString())
            }
        })

        alturaViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })

    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = AlturaAdapter(listaAltura,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaAltura() {
        alturaViewModel.listarAltura()
    }
    private fun listener() {
        binding.ibAddMantaltura.setOnClickListener{addAltura()}
        binding.ibUpdateMantaltura.setOnClickListener{updateAltura()}
        binding.ibDeleteMantaltura.setOnClickListener{deleteAltura()}
    }
    private fun searchAltura(){
        alturaViewModel.buscarAltura(idObtenida)
    }
    private fun addAltura() {
        accion = "añadio"
        val altura = Altura(
            null,
            binding.etNombreMantaltura.text.toString(),
            binding.etPrecioMantaltura.text.toString()
        )
        alturaViewModel.guardarAltura(altura)
    }
    private fun updateAltura() {
        val altura = Altura(
           idObtenida,
           binding.etNombreMantaltura.text.toString(),
           binding.etPrecioMantaltura.text.toString()
        )
        alturaViewModel.actualizarAltura(altura)
        listaAltura[position] = altura
        adapter.notifyItemChanged(position)
        showMessage("Altura actualizado")
    }
    private fun deleteAltura() {
        alturaViewModel.eliminarAltura(idObtenida)
        listaAltura.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Altura eliminado")
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onAlturaClick(id: Long,p:Int) {
        idObtenida = id
        position=p
        searchAltura()
    }
}