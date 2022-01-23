package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityMantDiametroBinding
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.view.adapter.DiametroAdapter
import mood.honeyprojects.gusgusapp.viewModel.DiametroViewModel

class MantDiametroActivity : AppCompatActivity(),DiametroAdapter.OnClickDiametroListener {

    private lateinit var binding: ActivityMantDiametroBinding
    private lateinit var adapter: DiametroAdapter
    private val diametroViewModel: DiametroViewModel by viewModels()
    private val listaDiametro = mutableListOf<Diametro>()
    private var accion:String = ""
    private var idObtenida:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMantDiametroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvDiametroMantdiametro)
        getListaDiametro()
        listener()
    }

    private fun initViewModel() {
        diametroViewModel.listaDiametroLiveData.observe(this,{
            if(it !=null){
                binding.rvDiametroMantdiametro.visibility = View.VISIBLE
                listaDiametro.clear()
                listaDiametro.addAll(it)
                adapter.notifyDataSetChanged()
                if(accion=="añadio"){
                    showMessage("Diametro agregado")
                }else if(accion=="actualizo"){
                    showMessage("Diametro actualizado")
                }else if(accion=="elimino"){
                    showMessage("Diametro eliminado")
                }
            }
        })

        diametroViewModel.diametroLiveData.observe(this,{
            if(it!=null){
                binding.etNombreMantdiametro.setText(it.descripcion.toString())
                binding.etPrecioMantdiametro.setText(it.precio.toString())
            }
        })

        diametroViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = DiametroAdapter(listaDiametro,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaDiametro() {
        diametroViewModel.listarDiametro()
    }
    private fun listener() {
        binding.ibAddMantdiametro.setOnClickListener{addDiametro()}
        binding.ibUpdateMantdiametro.setOnClickListener{updateDiametro()}
        binding.ibDeleteMantdiametro.setOnClickListener{deleteDiametro()}
    }
    private fun searchDiametro(){
        diametroViewModel.buscarDiametro(idObtenida)
    }
    private fun addDiametro() {
        val diametro = Diametro(
            null,
            binding.etNombreMantdiametro.text.toString(),
            binding.etPrecioMantdiametro.text.toString()
        )
        diametroViewModel.guardarDiametro(diametro)
        accion = "añadio"
        getListaDiametro()
        fillRecyclerView(binding.rvDiametroMantdiametro)
    }
    private fun updateDiametro() {
        val diametro = Diametro(
            idObtenida,
            binding.etNombreMantdiametro.text.toString(),
            binding.etPrecioMantdiametro.text.toString()
        )
        diametroViewModel.actualizarDiametro(diametro)
        accion = "actualizo"
        getListaDiametro()
        fillRecyclerView(binding.rvDiametroMantdiametro)
    }
    private fun deleteDiametro() {
        diametroViewModel.eliminarDiametro(idObtenida)
        accion = "elimino"
        getListaDiametro()
        fillRecyclerView(binding.rvDiametroMantdiametro)
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onDiametroClick(id: Long) {
        idObtenida = id
        searchDiametro()
    }
}