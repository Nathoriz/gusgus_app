package mood.honeyprojects.gusgusapp.view.ui
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.FragmentSearchBinding
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.view.adapter.ProductoSAdapter
import mood.honeyprojects.gusgusapp.viewModel.ProductoSViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: ProductoSAdapter
    private val productoSearchViewModel: ProductoSViewModel by viewModels()
    private val listaproducto = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate( inflater, container, false )
        binding.ivnotfound.visibility = View.INVISIBLE
        InitViewModel()
        GetProducto()
        InitRecyclerView( binding.rvproducto )
        FiltroProducto()

        return binding.root
    }

    private fun FiltroProducto(){
        binding.searchView.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchView.clearFocus()
                if (query != null) {
                    productoSearchViewModel.FiltroProducto( query )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    productoSearchViewModel.FiltroProducto( newText )
                }
                return false
            }
        } )
    }
    private fun GetProducto(){
        productoSearchViewModel.ListarProducts()
    }
    private fun InitRecyclerView( rv: RecyclerView ){
        adapter = ProductoSAdapter( listaproducto )
        rv.layoutManager = GridLayoutManager( context, 2 )
        rv.adapter = adapter
    }
    private fun InitViewModel(){
        productoSearchViewModel.listaProductoLiveData.observe( viewLifecycleOwner, Observer {
            if( it != null ){
                binding.rvproducto.visibility = View.VISIBLE
                binding.ivnotfound.visibility = View.INVISIBLE

                listaproducto.clear()
                listaproducto.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
        productoSearchViewModel.responseMensaje.observe( viewLifecycleOwner, Observer {
            if( it != null ){
                binding.rvproducto.visibility = View.INVISIBLE
                binding.ivnotfound.visibility = View.VISIBLE
            }
        } )
    }
}