package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityTortasPersonalizadasBinding
import mood.honeyprojects.gusgusapp.model.entity.Personalizacion
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.PedidoAdapter
import mood.honeyprojects.gusgusapp.view.adapter.TortasPersAdapter
import mood.honeyprojects.gusgusapp.viewModel.PersonalizacionViewModel

class TortasPersonalizadasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTortasPersonalizadasBinding
    private lateinit var adapter: TortasPersAdapter

    private val persTortasViewModel: PersonalizacionViewModel by viewModels()

    private val tortas = mutableListOf<Personalizacion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTortasPersonalizadasBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        GetTortasPersonalizadas()
        ViewModelTortasPers()
        RecyclerView( binding.rvTortaspersonalizadas )
        SearchNombreTorta()
    }

    private fun SearchNombreTorta(){
        binding.svSearchtortaspersonalizadas.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svSearchtortaspersonalizadas.clearFocus()
                if( query != null ){
                    val idClient = Preferences.constantes.getIDCliente()
                    persTortasViewModel.SearchNombreTorta( idClient, query )
                }
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if( newText != null ){
                    val idClient = Preferences.constantes.getIDCliente()
                    persTortasViewModel.SearchNombreTorta( idClient, newText )
                }
                return false
            }
        })
    }
    private fun RecyclerView( rv: RecyclerView ){
        adapter = TortasPersAdapter( tortas )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }
    private fun GetTortasPersonalizadas(){
        persTortasViewModel.GetListCakePersByIdClient( Preferences.constantes.getIDCliente() )
    }

    private fun ViewModelTortasPers(){
        persTortasViewModel.responsePersList.observe( this, Observer {
            if( it != null ){
                tortas.clear()
                tortas.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
    }
}