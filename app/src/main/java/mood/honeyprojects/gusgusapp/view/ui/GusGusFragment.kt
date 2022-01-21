package mood.honeyprojects.gusgusapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mood.honeyprojects.gusgusapp.databinding.FragmentGusGusBinding
import mood.honeyprojects.gusgusapp.listeners.CategoriaListener
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.CategoriaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ProductoAdapter
import mood.honeyprojects.gusgusapp.viewModel.CategoriaViewModel
import mood.honeyprojects.gusgusapp.viewModel.NoticiaViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GusGusFragment : Fragment(), CategoriaListener {
    //Adapters
    private lateinit var adapter: ProductoAdapter
    private lateinit var cateAdapter: CategoriaAdapter
    //Variables
    private lateinit var binding: FragmentGusGusBinding
    //ViewModels
    private val productoviewModel: ProductoViewModel by viewModels()
    private val categoriaViewModel: CategoriaViewModel by viewModels()
    private val noticiaViewModel: NoticiaViewModel by viewModels()
    //Listas
    val list = mutableListOf<String>()
    private val categorias = mutableListOf<Categoria>()
    private val productos = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGusGusBinding.inflate( inflater, container, false )

        InitViewModel()
        GetUrlNoticias()
        GetCategoria()
        GetProducto()
        InitRecyclerViewCate( binding.rvCategoriaGusgus )
        InitRecyclerView( binding.rvProductosGusgus )
        MostrarHora()
        return binding.root
    }

    private fun sliderList( carrusel: ImageCarousel, list: List<String> ){
        val listCarru = mutableListOf<CarouselItem>()
        for( url in list ){
            listCarru.add( CarouselItem(url) )
        }
        carrusel.addData( listCarru )
    }

    private fun InitRecyclerViewCate( rvCate: RecyclerView ){
        cateAdapter = CategoriaAdapter( categorias, this )
        rvCate.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false )
        rvCate.adapter = cateAdapter
    }

    private fun InitRecyclerView( rv: RecyclerView ){
        adapter = ProductoAdapter( productos )
        rv.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false )
        rv.adapter = adapter
    }

    private fun GetCategoria(){
        categoriaViewModel.ListarCategoria()
    }

    private fun MostrarHora(){
        lifecycleScope.launch{
            val hora = withContext( Dispatchers.IO ){ HoraActual() }
            val hestado = hora.substring( hora.length - 2 )
            val hhora = hora.substring( 0, 2 ).toInt()

            if( hestado == "AM" ){
                binding.tvGrettingGusgus.text = "Buenos Días"
            }else if( hhora in 12..18 && hestado == "PM" ){
                binding.tvGrettingGusgus.text = "Buenas Tardes"
            }else if( hhora in 19..23 || hhora.toString() == "00" && hestado == "PM" ){
                binding.tvGrettingGusgus.text = "Buenas Noches"
            }
        }
    }

    private fun HoraActual(): String {
        val dateformat: DateFormat = SimpleDateFormat("HH:mm a")
        val formatDate = dateformat.format(Date()).toString()
        return formatDate.substring(formatDate.length - formatDate.length)
    }

    private fun GetProducto(){
        productoviewModel.ListarProducts()
    }
    private fun GetUrlNoticias(){
        noticiaViewModel.FindNoticiasbyVisibilidad( true )
    }

    private fun InitViewModel(){
        productoviewModel.listaProductoLiveData.observe( viewLifecycleOwner, Observer {
            if( it != null ){
                productos.clear()
                productos.addAll( it )
                adapter.notifyDataSetChanged()
            }
        } )
        categoriaViewModel.listaCategoriaLiveData.observe( viewLifecycleOwner, Observer {
            if( it != null ){
                categorias.clear()
                categorias.addAll( it )
                cateAdapter.notifyDataSetChanged()
            }
        } )
        noticiaViewModel.urlNoticiasLiveData.observe( viewLifecycleOwner, Observer {
            if( it != null ){
                sliderList( binding.noticiasGusgus, it )
            }
        } )
    }

    override fun categoriaClicked(categoria: Categoria) {
        productoviewModel.ListarProductsPorCategoria( categoria.nombre )
    }
}