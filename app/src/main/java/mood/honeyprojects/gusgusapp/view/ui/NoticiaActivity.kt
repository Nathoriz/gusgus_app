package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
    private fun Listener(){
        binding.fabAgregarNoticia.setOnClickListener { AddNoticia() }
        binding.ivCloseNoticia.setOnClickListener { CloseNoticia() }
    }
    private fun AddNoticia(){
        val intent = Intent( this, AddNoticiaActivity::class.java )
        startActivity( intent )
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
                listNoticias.clear()
                listNoticias.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
    }
}