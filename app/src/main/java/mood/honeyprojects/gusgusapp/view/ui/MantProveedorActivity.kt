package mood.honeyprojects.gusgusapp.view.ui

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
import mood.honeyprojects.gusgusapp.view.adapter.AlturaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorAdapter
import mood.honeyprojects.gusgusapp.viewModel.AlturaViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProveedorViewModel

class MantProveedorActivity : AppCompatActivity(),MantProveedorAdapter.OnClickProveedorListener {
    private lateinit var binding: ActivityMantProveedorBinding
    private lateinit var adapter: MantProveedorAdapter
    private val proveedorViewModel: ProveedorViewModel by viewModels()
    private val listaProveedor = mutableListOf<Proveedor>()
    private var accion:String = ""
    private var idObtenida:Long = 0L
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        initViewModel()
        fillRecyclerView(binding.rvProveedoreMantproveedor)
        getListaProveedor()
        listener()
    }

    private fun initViewModel() {
        proveedorViewModel.listaProveedorLiveData.observe(this,{
            if(it !=null){
                binding.rvProveedoreMantproveedor.visibility = View.VISIBLE
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
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaProveedor() {
        proveedorViewModel.listarProveedor()
    }
    private fun listener() {
        binding.ibListMantproveedor.setOnClickListener {
            accion = "listar"
            binding.clGuardarMantproveedor.visibility = View.GONE
            binding.cvEliminarMantproveedor.visibility = View.GONE
            binding.rvProveedoreMantproveedor.visibility = View.VISIBLE
        }
        binding.ibAddMantproveedor.setOnClickListener{
            accion = "añadio"
            binding.rvProveedoreMantproveedor.visibility = View.GONE
            binding.cvEliminarMantproveedor.visibility = View.GONE
            binding.clGuardarMantproveedor.visibility = View.VISIBLE
        }
        binding.ibUpdateMantproveedor.setOnClickListener{
            accion = "actualizo"
            binding.rvProveedoreMantproveedor.visibility = View.GONE
            binding.cvEliminarMantproveedor.visibility = View.GONE
            binding.clGuardarMantproveedor.visibility = View.VISIBLE
        }
        binding.ibDeleteMantproveedor.setOnClickListener{
            accion = "elimino"
            binding.clGuardarMantproveedor.visibility = View.GONE
            binding.rvProveedoreMantproveedor.visibility = View.VISIBLE
            binding.cvEliminarMantproveedor.visibility = View.VISIBLE
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
            // ABRIR ACTIVITY MANT-PROVEEDOR-INSUMO
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
}