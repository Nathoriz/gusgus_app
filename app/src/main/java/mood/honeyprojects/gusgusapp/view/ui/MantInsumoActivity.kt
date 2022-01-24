package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import mood.honeyprojects.gusgusapp.databinding.ActivityMantDistritoBinding
import mood.honeyprojects.gusgusapp.databinding.ActivityMantInsumoBinding
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.view.adapter.DistritoAdapter
import mood.honeyprojects.gusgusapp.viewModel.DistritoViewModel

class MantInsumoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMantInsumoBinding
//    private lateinit var adapter: InsumoAdapter
//    private val insumoViewModel: InsumoViewModel by viewModels()
//    private val listaInsumo = mutableListOf<Insumo>()
    private var accion:String = ""
    private var idObtenida:Long = 0
    private var position:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMantInsumoBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}