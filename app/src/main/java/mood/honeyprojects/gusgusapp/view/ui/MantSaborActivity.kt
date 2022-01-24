package mood.honeyprojects.gusgusapp.view.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityMantDiametroBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantSaborBinding
import mood.honeyprojects.gusgusapp.model.entity.Diametro
import mood.honeyprojects.gusgusapp.model.entity.Sabor
import mood.honeyprojects.gusgusapp.view.adapter.DiametroAdapter
import mood.honeyprojects.gusgusapp.view.adapter.SaborAdapter
import mood.honeyprojects.gusgusapp.viewModel.DiametroViewModel
import mood.honeyprojects.gusgusapp.viewModel.SaborViewModel

class MantSaborActivity : AppCompatActivity(), SaborAdapter.OnClickSaborListener {
    private lateinit var binding: ActivityMantSaborBinding
    private lateinit var adapter: SaborAdapter
    private val saborViewModel: SaborViewModel by viewModels()
    private val listaSabor = mutableListOf<Sabor>()
    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantSaborBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvSaborMantsabor)
        getListaSabor()
        listener()
    }

    private fun initViewModel() {
        saborViewModel.listaSaborLiveData.observe(this,{
            if(it !=null){
                binding.rvSaborMantsabor.visibility = View.VISIBLE
                listaSabor.clear()
                listaSabor.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })

        saborViewModel.saborLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaSabor.add(it)
                    adapter.notifyItemInserted(listaSabor.size-1)
                    showMessage("Sabor agregado")
                    accion=""
                }
                binding.etNombreMantsabor.setText(it.nombre.toString())
                binding.etColorMantsabor.setText(it.color.toString())
                binding.vwBgcolorMantsabor.setBackgroundColor(Color.parseColor("#${it.color.toString()}"))
            }
        })

        saborViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = SaborAdapter(listaSabor,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaSabor() {
        saborViewModel.listarSabor()
    }
    private fun listener() {
        binding.ibAddMantsabor.setOnClickListener{addSabor()}
        binding.ibUpdateMantsabor.setOnClickListener{updateSabor()}
        binding.ibDeleteMantsabor.setOnClickListener{deleteSabor()}
    }
    private fun searchSabor(){
        saborViewModel.buscarSabor(idObtenida)
    }
    private fun addSabor() {
        accion = "añadio"
        val sabor = Sabor(
            null,
            binding.etNombreMantsabor.text.toString(),
            binding.etColorMantsabor.text.toString()
        )
        saborViewModel.guardarSabor(sabor)
    }
    private fun updateSabor() {
        val sabor = Sabor(
            idObtenida,
            binding.etNombreMantsabor.text.toString(),
            binding.etColorMantsabor.text.toString()
        )
        saborViewModel.actualizarSabor(sabor)
        listaSabor[position] = sabor
        adapter.notifyItemChanged(position)
        showMessage("Sabor actualizado")
    }
    private fun deleteSabor() {
        saborViewModel.eliminarSabor(idObtenida)
        listaSabor.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Sabor eliminado")
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onSaborClick(id: Long,p:Int) {
        idObtenida = id
        position = p
        searchSabor()
    }

    private fun colorPicker(){
        //TODO use color picker https://github.com/eltos/SimpleDialogFragments
    }
}