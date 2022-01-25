package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_add_noticia.*
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityMantAlturaBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantProveedorBinding
import mood.honeyprojects.gusgusapp.model.entity.Altura
import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import mood.honeyprojects.gusgusapp.model.entity.ProveedorInsumo
import mood.honeyprojects.gusgusapp.view.adapter.AlturaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorInsumoAdapter
import mood.honeyprojects.gusgusapp.viewModel.AlturaViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProveedorInsumoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProveedorViewModel

class MantProveedorActivity : AppCompatActivity(),MantProveedorAdapter.OnClickProveedorListener,MantProveedorInsumoAdapter.OnClickProveedorInsumoListener {
    private lateinit var binding: ActivityMantProveedorBinding

    private lateinit var adapter: MantProveedorAdapter
    private val proveedorViewModel: ProveedorViewModel by viewModels()
    private val listaProveedor = mutableListOf<Proveedor>()

    private lateinit var proveedorInsumoadapter: MantProveedorInsumoAdapter
    private val proveedorInsumoViewModel: ProveedorInsumoViewModel by viewModels()
    private val listaProveedorInsumo = mutableListOf<ProveedorInsumo>()

    private var accion:String = ""
    private var idObtenida:Long = 0L
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        initViewModel()
        fillRecyclerView(binding.rvProveedorMantproveedor)
        fillRecyclerViewProveedorInsumo(binding.rvProveedorinsumoMantproveedor)
        getListaProveedor()
        getListaProveedorInsumo()
        listener()

        var intentID = intent.getLongExtra("id",0L)
        if(intentID != 0L){
            fillRecyclerViewProveedorInsumo(binding.rvProveedorinsumoMantproveedor)
            getListaProveedorInsumo()
           // todo
        }
    }

    private fun initViewModel() {
        proveedorInsumoViewModel.listaProveedorInsumoLiveData.observe(this,{
            if(it !=null){
                listaProveedorInsumo.clear()
                listaProveedorInsumo.addAll(it)
                proveedorInsumoadapter.notifyDataSetChanged()
            }
        })

        proveedorViewModel.listaProveedorLiveData.observe(this,{
            if(it !=null){
                listaProveedor.clear()
                listaProveedor.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        proveedorViewModel.proveedorLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaProveedor.add(it)
                    adapter.notifyItemInserted(listaProveedor.size-1)
                    showMessage("Proveedor agregado")
                    accion=""
                    clear()
                }else{
                    binding.etNombreMantproveedor.setText(it.nombre.toString())
                    binding.etTelefonoMantproveedor.setText(it.telefono.toString())
                    binding.tvNombreMantproveedor.setText(it.nombre.toString())
                    binding.tvTelefonoMantproveedor.setText(it.telefono.toString())
                }
            }
        })
        proveedorViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = MantProveedorAdapter(listaProveedor,this)
        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter = adapter
    }
    private fun fillRecyclerViewProveedorInsumo(rv: RecyclerView) {
        proveedorInsumoadapter = MantProveedorInsumoAdapter(listaProveedorInsumo,this)
        rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rv.adapter = proveedorInsumoadapter
    }
    private fun getListaProveedor() {
        proveedorViewModel.listarProveedor()
    }
    private fun getListaProveedorInsumo() {
        proveedorInsumoViewModel.listarProveedorInsumo(idObtenida)
    }
    private fun listener() {
        binding.ibListMantproveedor.setOnClickListener {
            accion = "listar"
            binding.clGuardarMantproveedor.visibility = View.GONE
            binding.cvEliminarMantproveedor.visibility = View.GONE
            binding.rvProveedorMantproveedor.visibility = View.VISIBLE
        }
        binding.ibAddMantproveedor.setOnClickListener{
            accion = "añadio"
            binding.rvProveedorMantproveedor.visibility = View.GONE
            binding.cvEliminarMantproveedor.visibility = View.GONE
            binding.clGuardarMantproveedor.visibility = View.VISIBLE
            getListaProveedorInsumo()
        }
        binding.ibUpdateMantproveedor.setOnClickListener{
            accion = "actualizo"
            binding.rvProveedorMantproveedor.visibility = View.GONE
            binding.cvEliminarMantproveedor.visibility = View.GONE
            binding.clGuardarMantproveedor.visibility = View.VISIBLE
            getListaProveedorInsumo()
        }
        binding.ibDeleteMantproveedor.setOnClickListener{
            accion = "elimino"
            binding.clGuardarMantproveedor.visibility = View.GONE
            binding.rvProveedorMantproveedor.visibility = View.VISIBLE
            binding.cvEliminarMantproveedor.visibility = View.VISIBLE
            getListaProveedorInsumo()
        }
        binding.btnGuardarMantproveedor.setOnClickListener{
            if(accion=="añadio"){
                addProveedor()
            }else if(accion == "actualizo"){
                if(idObtenida != 0L){
                    updateProveedor()
                }else{
                    addProveedor()
                }
            }else{
                showMessage("Ups. Parece que ocurrio un problema :c")
            }
        }
        binding.btEliminarMantproveedor.setOnClickListener{
            if(accion=="elimino"){
                deleteProveedor()
            }
        }
        binding.btnInsumoMantproveedor.setOnClickListener{
            val intent = Intent( this, ProveedorInsumoActivity::class.java );
            intent.putExtra("id",idObtenida);
            showMessage(idObtenida.toString());
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
    }

    private fun searchProveedor(){
        proveedorViewModel.buscarProveedor(idObtenida)
    }
    private fun addProveedor() {
        val proveedor = Proveedor(
            null,
            binding.etNombreMantproveedor.text.toString(),
            binding.etTelefonoMantproveedor.text.toString()
        )
        proveedorViewModel.guardarProveedor(proveedor)
    }
    private fun updateProveedor() {
        val proveedor = Proveedor(
            idObtenida,
            binding.etNombreMantproveedor.text.toString(),
            binding.etTelefonoMantproveedor.text.toString()
        )
        proveedorViewModel.actualizarProveedor(proveedor)
        listaProveedor[position] = proveedor
        adapter.notifyItemChanged(position)
        showMessage("Proveedor actualizado")
        clear()
    }
    private fun deleteProveedor() {
        proveedorViewModel.eliminarProveedor(idObtenida)
        listaProveedor.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Proveedor eliminado")
        clear()
    }

    private fun clear(){
        binding.etNombreMantproveedor.setText("")
        binding.etTelefonoMantproveedor.setText("")
        binding.tvNombreMantproveedor.setText("")
        binding.tvTelefonoMantproveedor.setText("")

        idObtenida = 0L
        position = 0

    }
    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_LONG ).show()
    }
    override fun onProveedorClick(id: Long, p: Int) {
        idObtenida = id
        position= p
        searchProveedor()
    }

    override fun onProveedorInsumoClick(id: Long, position: Int) {
//        showMessage("insumo")
    }
}