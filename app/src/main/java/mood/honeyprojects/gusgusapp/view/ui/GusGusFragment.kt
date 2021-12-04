package mood.honeyprojects.gusgusapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mood.honeyprojects.gusgusapp.databinding.FragmentGusGusBinding
import mood.honeyprojects.gusgusapp.listeners.CategoriaListener
import mood.honeyprojects.gusgusapp.model.entity.Categoria
import mood.honeyprojects.gusgusapp.model.entity.Producto
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.view.adapter.CategoriaAdapter
import mood.honeyprojects.gusgusapp.view.adapter.ProductoAdapter
import mood.honeyprojects.gusgusapp.viewModel.CategoriaViewModel
import mood.honeyprojects.gusgusapp.viewModel.ProductoViewModel
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

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
        ShowNameUser()
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
        //LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        rv.layoutManager = GridLayoutManager( context, 2 )
        rv.adapter = adapter
    }
    private fun GetCategoria(){
        categoriaViewModel.ListarCategoria()
    }
    private fun GetProducto(){
        productoviewModel.ListarProducts()
    }
    private fun ShowNameUser(){
        binding.tvnombreUser.text = "Bienvenido(a) ${Preferences.constantes.getClientName() }"
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