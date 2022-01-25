package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.ActivityMantRecetaBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantRecetaInsumoBinding
import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import mood.honeyprojects.gusgusapp.model.entity.ProveedorInsumo
import mood.honeyprojects.gusgusapp.model.entity.Receta
import mood.honeyprojects.gusgusapp.model.entity.RecetaInsumo
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorInsumoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantRecetaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantRecetaIsumoAdapter
import mood.honeyprojects.gusgusapp.viewModel.ProveedorInsumoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProveedorViewModel
import mood.honeyprojects.gusgusapp.viewModel.RecetaInsumoViewModel
import mood.honeyprojects.gusgusapp.viewModel.RecetaViewModel

class MantRecetaActivity : AppCompatActivity(), MantRecetaAdapter.OnClickRecetaListener,MantRecetaIsumoAdapter.OnClickRecetaInsumoListener {
    private lateinit var binding:ActivityMantRecetaInsumoBinding

    private lateinit var adapter: MantRecetaAdapter
    private val recetaViewModel: RecetaViewModel by viewModels()
    private val listaReceta = mutableListOf<Receta>()

    private lateinit var recetaInsumoAdapter: MantRecetaIsumoAdapter
    private val recetaInsumoViewModel: RecetaInsumoViewModel by viewModels()
    private val listaRecetaInsumo = mutableListOf<RecetaInsumo>()

    private var accion:String = ""
    private var idObtenida:Long = 0L
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantRecetaInsumoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvRecetaMantreceta)
        fillRecyclerViewRecetaInsumo(binding.rvRecetainsumoMantreceta)
        getListaReceta()
        getListaRecetaInsumo()
        listener()

        var intentID = intent.getLongExtra("id",0L)
        if(intentID != 0L){
            fillRecyclerViewRecetaInsumo(binding.rvRecetainsumoMantreceta)
            getListaRecetaInsumo()
        }

    }

    private fun initViewModel() {
        recetaInsumoViewModel.listaRecetaInsumoLiveData.observe(this,{
            if(it !=null){
                listaRecetaInsumo.clear()
                listaRecetaInsumo.addAll(it)
                recetaInsumoAdapter.notifyDataSetChanged()
            }
        })

        recetaViewModel.listaRecetaLiveData.observe(this,{
            if(it !=null){
                listaReceta.clear()
                listaReceta.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        recetaViewModel.recetaLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaReceta.add(it)
                    adapter.notifyItemInserted(listaReceta.size-1)
                    showMessage("Receta agregada")
                    accion=""
                    clear()
                }else{
                    binding.etDescropcionMantreceta.setText(it.descripcion.toString())
                    binding.etPrecioMantreceta.setText(it.costoProduccion.toString())
                    binding.etTiempoMantreceta.setText(it.tiempoProduccion.toString())
                    binding.tvDescripcionMantreceta.text = it.descripcion.toString()
                    binding.tvPrecioMantreceta.text = it.costoProduccion.toString()
                    binding.tvTiempoMantreceta.text = it.tiempoProduccion.toString()
                }
            }
        })
        recetaViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = MantRecetaAdapter(listaReceta,this)
        rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv.adapter = adapter
    }
    private fun fillRecyclerViewRecetaInsumo(rv: RecyclerView) {
        recetaInsumoAdapter = MantRecetaIsumoAdapter(listaRecetaInsumo,this)
        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter = recetaInsumoAdapter
    }
    private fun getListaReceta() {
        recetaViewModel.listarReceta()
    }
    private fun getListaRecetaInsumo() {
        recetaInsumoViewModel.listarRecetaInsumo(idObtenida)
    }
    private fun listener() {
        binding.ibListMantreceta.setOnClickListener {
            accion = "listar"
            binding.clGuardarMantreceta.visibility = View.GONE
            binding.cvEliminarMantreceta.visibility = View.GONE
            binding.rvRecetaMantreceta.visibility = View.VISIBLE
        }
        binding.ibAddMantreceta.setOnClickListener{
            accion = "añadio"
            binding.rvRecetaMantreceta.visibility = View.GONE
            binding.cvEliminarMantreceta.visibility = View.GONE
            binding.clGuardarMantreceta.visibility = View.VISIBLE
            getListaRecetaInsumo()
        }
        binding.ibUpdateMantreceta.setOnClickListener{
            accion = "actualizo"
            binding.rvRecetaMantreceta.visibility = View.GONE
            binding.cvEliminarMantreceta.visibility = View.GONE
            binding.clGuardarMantreceta.visibility = View.VISIBLE
            getListaRecetaInsumo()
        }
        binding.ibDeleteMantreceta.setOnClickListener{
            accion = "elimino"
            binding.clGuardarMantreceta.visibility = View.GONE
            binding.rvRecetaMantreceta.visibility = View.VISIBLE
            binding.cvEliminarMantreceta.visibility = View.VISIBLE
            getListaRecetaInsumo()
        }
        binding.btnGuardarMantreceta.setOnClickListener{
            if(accion=="añadio"){
                addReceta()
            }else if(accion == "actualizo") {
                if (idObtenida != 0L) {
                    updateReceta()
                } else {
                    addReceta()
                }
            }
            else{
                showMessage("Ups. Parece que ocurrio un problema :c")
            }
        }
        binding.btnEliminarMantreceta.setOnClickListener{
            if(accion=="elimino"){
                deleteReceta()
            }
        }
        binding.btnInusmoMantreceta.setOnClickListener{
            val intent = Intent( this, MantRecetaInsumoActivity::class.java );
            intent.putExtra("id",idObtenida);
            showMessage(idObtenida.toString());
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
    }

    private fun searchReceta(){
        recetaViewModel.buscarReceta(idObtenida)
    }
    private fun addReceta() {
        val receta = Receta(
            null,
            binding.etDescropcionMantreceta.text.toString(),
            binding.etTiempoMantreceta.text.toString().toDouble(),
            binding.etPrecioMantreceta.text.toString().toDouble()
        )
        recetaViewModel.guardarReceta(receta)
    }
    private fun updateReceta() {
        val receta = Receta(
            idObtenida,
            binding.etDescropcionMantreceta.text.toString(),
            binding.etTiempoMantreceta.text.toString().toDouble(),
            binding.etPrecioMantreceta.text.toString().toDouble()
        )
        recetaViewModel.actualizarReceta(receta)
        listaReceta[position] = receta
        adapter.notifyItemChanged(position)
        showMessage("Receta actualizada")
        clear()
    }
    private fun deleteReceta() {
        recetaViewModel.eliminarReceta(idObtenida)
        listaReceta.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Receta eliminada")
        clear()
    }

    private fun clear(){
        binding.etDescropcionMantreceta.setText("")
        binding.etTiempoMantreceta.setText("")
        binding.etPrecioMantreceta.setText("")
        binding.tvDescripcionMantreceta.text = ""
        binding.tvTiempoMantreceta.text = ""
        binding.tvPrecioMantreceta.text = ""

        idObtenida = 0L
        position = 0
    }
    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }

    override fun onRecetaClick(id: Long, p: Int) {
        idObtenida = id
        position= p
        searchReceta()
    }
    override fun onRecetaInsumoClick(id: Long, position: Int) {

    }

}