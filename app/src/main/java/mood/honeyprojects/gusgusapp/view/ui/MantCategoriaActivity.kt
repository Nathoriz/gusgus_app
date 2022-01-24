package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mood.honeyprojects.gusgusapp.databinding.ActivityMantCategoriaBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantInsumoBinding

class MantCategoriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMantCategoriaBinding
    //    private lateinit var adapter: CategoriaAdapter
//    private val categoriaViewModel: CategoriaViewModel by viewModels()
//    private val listaCategoria = mutableListOf<Categoria>()
    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}