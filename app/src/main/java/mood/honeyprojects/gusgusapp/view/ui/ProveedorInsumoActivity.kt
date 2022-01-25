package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityMantProveedorBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityProveedorInsumoBinding
import mood.honeyprojects.gusgusapp.model.entity.Insumo
import mood.honeyprojects.gusgusapp.model.entity.Proveedor
import mood.honeyprojects.gusgusapp.model.entity.ProveedorInsumo
import mood.honeyprojects.gusgusapp.model.entity.Visibilidad
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorInsumoAdapter
import mood.honeyprojects.gusgusapp.viewModel.InsumoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProveedorInsumoViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProveedorViewModel

class ProveedorInsumoActivity : AppCompatActivity(), MantProveedorInsumoAdapter.OnClickProveedorInsumoListener {
    private lateinit var binding: ActivityProveedorInsumoBinding
    private lateinit var adapter: MantProveedorInsumoAdapter
    private val proveedorInsumoViewModel: ProveedorInsumoViewModel by viewModels()
    private val listaProveedorInsumo = mutableListOf<ProveedorInsumo>()

    private val insumoViewModel: InsumoViewModel by viewModels()
    private val listaNombreInsumo = mutableListOf<String>()

    private val proveedorViewModel: ProveedorViewModel by viewModels()

    private var accion:String = ""
    private var idObtenida:Long = 0L
    private var position:Int = 0

    private var idProveedor:Long = 0L
    private var nombreInsumo:String = ""

    private var proveedor: Proveedor?=null
    private var insumo: Insumo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProveedorInsumoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idProveedor = intent.getLongExtra("id",0L)

        initViewModel()
        fillRecyclerView(binding.rvProveedorinsumoMantproveedorinsumo)
        getListaProveedorInsumo()
        getListaInsumo()
        listener()
    }

    private fun initViewModel() {
        //Insumo
        insumoViewModel.listaNombreInsumo.observe(this,{
            if(it !=null){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spInsumosMantproveedorinsumos.adapter = adapter

                binding.spInsumosMantproveedorinsumos.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreInsumo = it[ int ]
                        Toast.makeText( this@ProveedorInsumoActivity, nombreInsumo, Toast.LENGTH_LONG ).show()
                        searchInsumo( nombreInsumo!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        })
        insumoViewModel.insumoLiveDate.observe(this,{
            if(it!=null){
                insumo = it
            }
        })
        //ProveedorInsumo
        proveedorInsumoViewModel.listaProveedorInsumoLiveData.observe(this,{
            if(it !=null){
                binding.rvProveedorinsumoMantproveedorinsumo.visibility = View.VISIBLE
                listaProveedorInsumo.clear()
                listaProveedorInsumo.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        proveedorInsumoViewModel.proveedorInsumoLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaProveedorInsumo.add(it)
                    adapter.notifyItemInserted(listaProveedorInsumo.size-1)
                    showMessage("Insumo agregado")
                    accion=""
                    clear()
                }else{
                    binding.etPrecioMantproveedorinsumo.setText(it.precio.toString())
                    getListaInsumo()
                }
            }
        })
        proveedorInsumoViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }
    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = MantProveedorInsumoAdapter(listaProveedorInsumo,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }
    private fun getListaProveedorInsumo() {
        proveedorInsumoViewModel.listarProveedorInsumo(idProveedor)
    }
    private fun getListaInsumo() {
        insumoViewModel.listarNombreInsumo()
    }
    private fun listener() {
        binding.ibAddMantproveedorinsumo.setOnClickListener {
            addInsumo()
        }
        binding.ibUpdateMantproveedorinsumo.setOnClickListener{
            updateInsumo()
        }
        binding.ibDeleteMantproveedorinsumo.setOnClickListener{
            deleteInusmo()
        }
        binding.ivBackMantproveedorinsumo.setOnClickListener{
            val intent = Intent( this, MantProveedorActivity::class.java );
            intent.putExtra("id",idProveedor);
            showMessage(idProveedor.toString());
            showMessage(accion);

//            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
//            startActivity( intent )
        }
        binding.ivHideMantproveedorinsumo.setOnClickListener{
            binding.rvProveedorinsumoMantproveedorinsumo.visibility = View.GONE
        }
        binding.ivShowMantproveedorinsumo.setOnClickListener{
            binding.rvProveedorinsumoMantproveedorinsumo.visibility = View.VISIBLE
        }
    }

    private fun searchProveedorInsumo(){
        proveedorInsumoViewModel.buscarProveedorInsumo(idObtenida)
    }
    private fun searchInsumo(nombreInsumo: String) {
        insumoViewModel.buscarInsumoPorNombre(nombreInsumo)
    }
    private fun addInsumo() {
        val proveedorInsumo = ProveedorInsumo(
            null,
                proveedor,
                insumo,
                binding.etPrecioMantproveedorinsumo.text.toString().toDouble()
        )
        proveedorInsumoViewModel.guardarProveedorInsumo(proveedorInsumo)
    }

    private fun updateInsumo() {
        val proveedorInsumo = ProveedorInsumo(
            idObtenida,
            proveedor,
            insumo,
            binding.etPrecioMantproveedorinsumo.text.toString().toDouble()
        )
        proveedorInsumoViewModel.actualizarProveedorInsumo(proveedorInsumo)
        listaProveedorInsumo[position] = proveedorInsumo
        adapter.notifyItemChanged(position)
        showMessage("Insumo actualizado")
        clear()
    }

    private fun deleteInusmo() {
        proveedorInsumoViewModel.eliminarProveedorInsumo(idObtenida)
        listaProveedorInsumo.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Proveedor eliminado")
        clear()
    }

    private fun clear() {
        binding.etPrecioMantproveedorinsumo.setText("")
        getListaInsumo()

        idObtenida = 0L
        position = 0
    }

    private fun showMessage( message: String ){
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }

    override fun onProveedorInsumoClick(id: Long, p: Int) {
        idObtenida = id
        position = p
        searchProveedorInsumo()
    }
}