package mood.honeyprojects.gusgusapp.view.ui

import android.R
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
import mood.honeyprojects.gusgusapp.databinding.ActivityMantRecetaBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantRecetaInsumoBinding
import mood.honeyprojects.gusgusapp.model.entity.*
import mood.honeyprojects.gusgusapp.view.adapter.MantProveedorInsumoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.MantRecetaIsumoAdapter
import mood.honeyprojects.gusgusapp.viewModel.*

class MantRecetaInsumoActivity : AppCompatActivity(), MantRecetaIsumoAdapter.OnClickRecetaInsumoListener {
    private lateinit var binding:ActivityMantRecetaBinding
    private lateinit var adapter: MantRecetaIsumoAdapter
    private val recetaInsumoViewModel: RecetaInsumoViewModel by viewModels()
    private val listaRecetaInsumo = mutableListOf<RecetaInsumo>()

    private val insumoViewModel: InsumoViewModel by viewModels()
    private val listaNombreInsumo = mutableListOf<String>()
    private var insumo: Insumo? = null
    private var nombreInsumo:String = ""

    private val recetaViewModel: RecetaViewModel by viewModels()
    private var receta: Receta? = null

    private var accion:String = ""
    private var idObtenida:Long = 0L
    private var position:Int = 0

    private var idReceta: Long = 0L






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        idReceta = intent.getLongExtra("id",0L)

        initViewModel()
        fillRecyclerView(binding.rvRecetainsumoMantrecetainsumo)
        searchReceta()
        getListaRecetaInsumo()
        getListaInsumo()
        listener()
    }

    private fun initViewModel() {
        //Insumo
        insumoViewModel.listaNombreInsumo.observe(this,{
            if(it !=null){
                listaNombreInsumo.addAll(it)
                val adapter = ArrayAdapter( this, R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spInsumosMantrecetainsumo.adapter = adapter

                binding.spInsumosMantrecetainsumo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreInsumo = it[ int ]
                        Toast.makeText( this@MantRecetaInsumoActivity, nombreInsumo, Toast.LENGTH_LONG ).show()
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
        //Proveedor
        recetaViewModel.recetaLiveData.observe(this,{
            if(it!=null){
                receta = it
            }
        })
        //RecetaInsumo
        recetaInsumoViewModel.listaRecetaInsumoLiveData.observe(this,{
            if(it !=null){
                binding.rvRecetainsumoMantrecetainsumo.visibility = View.VISIBLE
                listaRecetaInsumo.clear()
                listaRecetaInsumo.addAll(it)
                adapter.notifyDataSetChanged()
            }
        })
        recetaInsumoViewModel.recetaInsumoLiveData.observe(this,{
            if(it!=null){
                if(accion=="añadio"){
                    listaRecetaInsumo.add(it)
                    adapter.notifyItemInserted(listaRecetaInsumo.size-1)
                    showMessage("Insumo agregado")
                    accion=""
                    clear()
                }else{
                    binding.etCantidadMantrecetainsumo.setText(it.cantidadUso.toString())
                    var nombreInsumo = it.insumo?.nombre.toString()
                    var int = listaNombreInsumo.indexOf(nombreInsumo)
                    binding.spInsumosMantrecetainsumo.setSelection(int)
                }
            }
        })
        recetaInsumoViewModel.messageResponse.observe(this,{
            if(it!=null){
                showMessage(it)
            }
        })
    }

    private fun fillRecyclerView(rv: RecyclerView) {
        adapter = MantRecetaIsumoAdapter(listaRecetaInsumo,this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    private fun getListaRecetaInsumo() {
        recetaInsumoViewModel.listarRecetaInsumo(idReceta)
    }
    private fun getListaInsumo() {
        insumoViewModel.listarNombreInsumo()
    }

    private fun listener() {
        binding.ibAddMantrecetainsumo.setOnClickListener {
            addInsumo()
        }
        binding.ibUpdateMantrecetainsumo.setOnClickListener{
            updateInsumo()
        }
        binding.ibDeleteMantrecetainsumo.setOnClickListener{
            deleteInusmo()
        }
        binding.ivBackMantrectainsumo.setOnClickListener{
            val intent = Intent( this, MantRecetaActivity::class.java );
            intent.putExtra("id",idReceta);
            showMessage(idReceta.toString());
            intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            startActivity( intent )
        }
        binding.ivHideMantrecetainsumo.setOnClickListener{
            binding.ivHideMantrecetainsumo.visibility = View.GONE
            binding.rvRecetainsumoMantrecetainsumo.visibility = View.GONE
            binding.ivShowMantrecetainsumo.visibility = View.VISIBLE
        }
        binding.ivShowMantrecetainsumo.setOnClickListener{
            binding.ivShowMantrecetainsumo.visibility = View.GONE
            binding.ivHideMantrecetainsumo.visibility = View.VISIBLE
            binding.rvRecetainsumoMantrecetainsumo.visibility = View.VISIBLE
        }
    }

    private fun searchRecetaInsumo(){
        recetaInsumoViewModel.buscarRecetaInsumo(idObtenida)
    }

    private fun searchReceta() {
        recetaViewModel.buscarReceta(idReceta)
    }

    private fun searchInsumo(nombreInsumo: String) {
        insumoViewModel.buscarInsumoPorNombre(nombreInsumo)
    }

    private fun addInsumo() {
        accion="añadio"
        val recetaInsumo = RecetaInsumo(
            null,
            receta,
            insumo,
            binding.etCantidadMantrecetainsumo.text.toString()
        )
        recetaInsumoViewModel.guardarRecetaInsumo(recetaInsumo)
    }
    private fun updateInsumo() {
        val recetaInsumo = RecetaInsumo(
            idObtenida,
            receta,
            insumo,
            binding.etCantidadMantrecetainsumo.text.toString()
        )
        recetaInsumoViewModel.actualizarRecetaInsumo(recetaInsumo)
        listaRecetaInsumo[position] = recetaInsumo
        adapter.notifyItemChanged(position)
        showMessage("Insumo actualizado")
        clear()
    }
    private fun deleteInusmo() {
        recetaInsumoViewModel.eliminarRecetaInsumo(idObtenida)
        listaRecetaInsumo.removeAt(position)
        adapter.notifyItemRemoved(position)
        showMessage("Insumo eliminado")
        clear()
    }

    private fun clear() {
        binding.etCantidadMantrecetainsumo.setText("")
        getListaInsumo()
        idObtenida = 0L
        position = 0
    }


    private fun showMessage(message: String) {
        Toast.makeText( this, message, Toast.LENGTH_SHORT ).show()
    }

    override fun onRecetaInsumoClick(id: Long, p: Int) {
        idObtenida = id
        position = p
        searchRecetaInsumo()
    }
}