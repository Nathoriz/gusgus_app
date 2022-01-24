package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityManRellenoBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantDiametroBinding
import mood.honeyprojects.gusgusapp.model.entity.Cubierta
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.entity.Relleno
import mood.honeyprojects.gusgusapp.view.adapter.CubiertaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.DiametroAdapter
import mood.honeyprojects.gusgusapp.view.adapter.RellenoAdapter
import mood.honeyprojects.gusgusapp.viewModel.CubiertaViewModel
import mood.honeyprojects.gusgusapp.viewModel.DiametroViewModel
import mood.honeyprojects.gusgusapp.viewModel.RellenoViewModel

class ManRellenoActivity : AppCompatActivity(), RellenoAdapter.OnClickRellenoListener {
    private lateinit var binding: ActivityManRellenoBinding
    private lateinit var adapter: RellenoAdapter
    private val rellenoViewModel: RellenoViewModel by viewModels()
    private val listaRelleno = mutableListOf<Relleno>()
    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManRellenoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvRellenosMantrelleno)
        getListaRelleno()
        listener()
    }

    private fun initViewModel() {
        rellenoViewModel.listaRellenoLiveData.observe(this,{
            if(it !=null){
                binding.rvRellenosMantrelleno.visibility = View.VISIBLE
                listaRelleno.clear()
                listaRelleno.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        rellenoViewModel.rellenoLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaRelleno.add(it)
                    adapter.notifyItemInserted(listaRelleno.size-1)
                    showMessage("Relleno agregado")
                    accion=""
                }
                binding.etNombreMantrelleno.setText(it.descripcion.toString())
            }
        })

        rellenoViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = RellenoAdapter(listaRelleno,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaRelleno() {
        rellenoViewModel.listarRelleno()
    }
    private fun listener() {
        binding.ibAddMantrelleno.setOnClickListener{addRelleno()}
        binding.ibUpdateMantrelleno.setOnClickListener{updateRelleno()}
        binding.ibDeleteMantrelleno.setOnClickListener{deleteRelleno()}
    }
    private fun searchRelleno(){
        rellenoViewModel.buscarRelleno(idObtenida)
    }
    private fun addRelleno() {
        accion = "añadio"
        val relleno = Relleno(
            null,
            binding.etNombreMantrelleno.text.toString()
        )
        rellenoViewModel.guardarRelleno(relleno)
    }
    private fun updateRelleno() {
        val relleno = Relleno(
            idObtenida,
            binding.etNombreMantrelleno.text.toString()
        )
        rellenoViewModel.actualizarRelleno(relleno)
        listaRelleno[position] = relleno
        adapter.notifyItemChanged(position)
        showMessage("Relleno actualizado")
    }
    private fun deleteRelleno() {
        rellenoViewModel.eliminarRelleno(idObtenida)
        listaRelleno.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Relleno eliminado")
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onRellenoClick(id: Long,p:Int) {
        idObtenida = id
        position = p
        searchRelleno()
    }
}