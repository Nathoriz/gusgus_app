package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityMantDiametroBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantDistritoBinding
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.view.adapter.DiametroAdapter
import mood.honeyprojects.gusgusapp.view.adapter.DistritoAdapter
import mood.honeyprojects.gusgusapp.viewModel.DiametroViewModel
import mood.honeyprojects.gusgusapp.viewModel.DistritoViewModel

class MantDistritoActivity : AppCompatActivity(),DistritoAdapter.OnClickDistritoListener {
    private lateinit var binding: ActivityMantDistritoBinding
    private lateinit var adapter: DistritoAdapter
    private val distritoViewModel: DistritoViewModel by viewModels()
    private val listaDistrito = mutableListOf<Distrito>()
    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantDistritoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvDistritoMantdistrito)
        getListaDistrito()
        listener()
    }

    private fun initViewModel() {
        distritoViewModel.listaDistritoLiveData.observe(this,{
            if(it !=null){
                binding.rvDistritoMantdistrito.visibility = View.VISIBLE
                listaDistrito.clear()
                listaDistrito.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        distritoViewModel.distritoLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaDistrito.add(it)
                    adapter.notifyItemInserted(listaDistrito.size-1)
                    showMessage("Distrito agregado")
                    accion=""
                }
                binding.etNombreMantdistrito.setText(it.nombre.toString())
                binding.etPrecioMantdistrito.setText(it.precio.toString())
            }
        })

        distritoViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = DistritoAdapter(listaDistrito,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaDistrito() {
        distritoViewModel.listarDistrito()
    }
    private fun listener() {
        binding.ibAddMantdistrito.setOnClickListener{addDistrito()}
        binding.ibUpdateMantdistrito.setOnClickListener{updateDistrito()}
        binding.ibDeleteMantdistrito.setOnClickListener{deleteDistrito()}
    }
    private fun searchDistrito(){
        distritoViewModel.buscarDistrito(idObtenida)
    }
    private fun addDistrito() {
        accion = "añadio"
        val distrito = Distrito(
            null,
            binding.etNombreMantdistrito.text.toString(),
            binding.etPrecioMantdistrito.text.toString()
        )
        distritoViewModel.guardarDistrito(distrito)
    }
    private fun updateDistrito() {
        val distrito = Distrito(
            idObtenida,
            binding.etNombreMantdistrito.text.toString(),
            binding.etPrecioMantdistrito.text.toString()
        )
        distritoViewModel.actualizarDistrito(distrito)
        listaDistrito[position] = distrito
        adapter.notifyItemChanged(position)
        showMessage("Distrito actualizado")
    }
    private fun deleteDistrito() {
        distritoViewModel.eliminarDistrito(idObtenida)
        listaDistrito.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Distrito eliminado")
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onDistritoClick(id: Long,p:Int) {
        idObtenida = id
        position = p
        searchDistrito()
    }
}