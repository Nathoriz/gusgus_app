package mood.honeyprojects.gusgusapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import mood.honeyprojects.gusgusapp.view.adapter.CategoriaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ProductoAdapter
import mood.honeyprojects.gusgusapp.viewModel.CategoriaViewModel
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
    //Listas
    val list = mutableListOf<CarouselItem>()
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
        GetCategoria()
        GetProducto()
        InitRecyclerViewCate( binding.rvcategoria )
        InitRecyclerView( binding.rvproducto )
        MostrarHora()
        //ShowNameUser()
        sliderList( binding.carousel )

        return binding.root
    }
    private fun sliderList( carrusel: ImageCarousel ){
        list.add( CarouselItem("https://i.ibb.co/y8xXFNK/publicidad-Uno.png") )
        list.add( CarouselItem("https://i.ibb.co/JRhPwTj/Publicidad-Dos.png") )
        list.add( CarouselItem("https://i.ibb.co/ysYBb9n/Publicidadtres.png") )
        carrusel.addData( list )
    }
    private fun InitRecyclerViewCate( rvCate: RecyclerView ){
        cateAdapter = CategoriaAdapter( categorias, this )
        rvCate.layoutManager = LinearLayoutManager( context, LinearLayoutManager.HORIZONTAL, false )
        rvCate.adapter = cateAdapter
    }
    private fun InitRecyclerView( rv: RecyclerView ){
        adapter = ProductoAdapter( productos )
        rv.layoutManager = GridLayoutManager( context, 2 )
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
                binding.tvnombreUser.text = "Buenos Días"
            }else if( hhora in 12..18 && hestado == "PM" ){
                binding.tvnombreUser.text = "Buenas Tardes"
            }else if( hhora in 19..23 || hhora.toString() == "00" && hestado == "PM" ){
                binding.tvnombreUser.text = "Buenas Noches"
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
    }

    override fun categoriaClicked(categoria: Categoria) {
        productoviewModel.ListarProductsPorCategoria( categoria.nombre )
    }
}