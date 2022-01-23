package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityMantCubiertaBinding
import mood.honeyprojects.gusgusapp.model.entity.Cubierta
import mood.honeyprojects.gusgusapp.view.adapter.CubiertaAdapter
import mood.honeyprojects.gusgusapp.viewModel.CubiertaViewModel

class MantCubiertaActivity : AppCompatActivity(), CubiertaAdapter.OnClickCubiertaListener {
    private lateinit var binding: ActivityMantCubiertaBinding
    private lateinit var adapter: CubiertaAdapter
    private val cubiertaViewModel: CubiertaViewModel by viewModels()
    private val listaCubierta = mutableListOf<Cubierta>()
    private var accion:String = ""
    private var idObtenida:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantCubiertaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvCubiertaMantcubierta)
        getListaCubierta()
        listener()
    }

    private fun initViewModel() {
        cubiertaViewModel.listaCubiertaLiveData.observe(this,{
            if(it !=null){
                binding.rvCubiertaMantcubierta.visibility = View.VISIBLE
                listaCubierta.clear()
                listaCubierta.addAll(it)
                adapter.notifyDataSetChanged()
                if(accion=="añadio"){
                    showMessage("Cubierta agregada")
                }else if(accion=="actualizo"){
                    showMessage("Cubierta actualizada")
                }else if(accion=="elimino"){
                    showMessage("Cubierta eliminada")
                }
            }
        })

        cubiertaViewModel.cubiertaLiveData.observe(this,{
            if(it!=null){
                binding.etNombreMantcubierta.setText(it.nombre.toString())
            }
        })

        cubiertaViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }

    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = CubiertaAdapter(listaCubierta,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    private fun getListaCubierta() {
        cubiertaViewModel.listarCubierta()
    }

    private fun listener() {
        binding.ibAddMantcubierta.setOnClickListener{addCubierta()}
        binding.ibUpdateMantcubierta.setOnClickListener{updateCubierta()}
        binding.ibDeleteMantcubierta.setOnClickListener{deleteCubierta()}
    }

    private fun searchCubierta(){
        cubiertaViewModel.buscarCubierta(idObtenida)
    }

    private fun addCubierta() {
        val cubierta = Cubierta(
            null,
            binding.etNombreMantcubierta.text.toString()
        )
        cubiertaViewModel.guardarCubierta(cubierta)
        accion = "añadio"
        getListaCubierta()
        fillRecyclerView(binding.rvCubiertaMantcubierta)
    }

    private fun updateCubierta() {
        val cubierta = Cubierta(
            idObtenida,
            binding.etNombreMantcubierta.text.toString()
        )
        cubiertaViewModel.actualizarCubierta(cubierta)
        accion = "actualizo"
        getListaCubierta()
        fillRecyclerView(binding.rvCubiertaMantcubierta)
    }

    private fun deleteCubierta() {
        cubiertaViewModel.eliminarCubierta(idObtenida)
        accion="elimino"
        getListaCubierta()
        fillRecyclerView(binding.rvCubiertaMantcubierta)
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onCubiertaClick(id: Long) {
        idObtenida = id
        searchCubierta()
    }
}