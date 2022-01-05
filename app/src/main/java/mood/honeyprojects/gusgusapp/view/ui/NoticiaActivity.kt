package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityNoticiaBinding
import mood.honeyprojects.gusgusapp.model.entity.Noticias
import mood.honeyprojects.gusgusapp.view.adapter.NoticiaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.NoticiaViewHolder
import mood.honeyprojects.gusgusapp.view.adapter.ProductoAdapter
import mood.honeyprojects.gusgusapp.viewModel.NoticiaViewModel

class NoticiaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoticiaBinding
    private lateinit var adapter: NoticiaAdapter
    private val noticiaViewModel: NoticiaViewModel by viewModels()

    private val listNoticias = mutableListOf<Noticias>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiaBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()

        InitViewModel()
        InitRecyclerView( binding.rvNoticiasNoticia )
        GetNoticias()
        Listener()
        FiltroNoticia()
    }
    private fun Listener(){
        binding.fabAgregarNoticia.setOnClickListener { AddNoticia() }
        binding.ivCloseNoticia.setOnClickListener { CloseNoticia() }
    }
    private fun AddNoticia(){
        val intent = Intent( this, AddNoticiaActivity::class.java )
        startActivity( intent )
    }
    private fun FiltroNoticia(){
        binding.svNoticiasNoticia.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.svNoticiasNoticia.clearFocus()
                if (query != null) {
                    noticiaViewModel.FiltroNoticias( query )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    noticiaViewModel.FiltroNoticias( newText )
                }
                return false
            }
        } )
    }
    private fun CloseNoticia(){
        val intent = Intent( this, AdminMainActivity::class.java )
        startActivity( intent )
        finish()
    }
    private fun GetNoticias(){
        noticiaViewModel.ListarNoticias()
    }
    private fun InitRecyclerView( rv: RecyclerView ){
        adapter = NoticiaAdapter( listNoticias )
        rv.layoutManager = LinearLayoutManager( this )
        rv.adapter = adapter
    }
    private fun InitViewModel(){
        noticiaViewModel.noticiasLiveData.observe( this, Observer {
            if( it != null ){
                binding.rvNoticiasNoticia.visibility = View.VISIBLE
                listNoticias.clear()
                listNoticias.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
        noticiaViewModel.messageResponse.observe( this, Observer {
            if( it != null ){
                binding.rvNoticiasNoticia.visibility = View.INVISIBLE
            }
        } )
    }
}