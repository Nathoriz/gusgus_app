package mood.honeyprojects.gusgusapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import mood.honeyprojects.gusgusapp.databinding.ActivitySignupBinding
import mood.honeyprojects.gusgusapp.listeners.ValiRegisterClient
import mood.honeyprojects.gusgusapp.model.entity.Cliente
import mood.honeyprojects.gusgusapp.model.entity.Distrito
import mood.honeyprojects.gusgusapp.sharedPreferences.Preferences
import mood.honeyprojects.gusgusapp.viewModel.ClienteViewModel
import mood.honeyprojects.gusgusapp.viewModel.DistritoViewModel

class SignupActivity : AppCompatActivity(), ValiRegisterClient {
    //Variables
    private lateinit var binding: ActivitySignupBinding
    private val clienteViewModel: ClienteViewModel by viewModels()
    private val distritoViewModel: DistritoViewModel by viewModels()
    private var distrito: Distrito?=null
    private var nombreDistri: String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //Metodos
        InitViewModel()
        GetListDistrito()
        Listeners()

    }
    private fun Listeners(){
        binding.btnRegisCliente.setOnClickListener {
            RegistrarClient()
        }
    }
    private fun GetListDistrito(){
        distritoViewModel.ListDistrito()
    }
    private fun BuscarDistrito( nombre: String ){
        distritoViewModel.BuscarDistrito( nombre )
    }
    private fun RegistrarClient(){
        val cliente = Cliente(
            null,
            binding.txtNombre.text.toString(),
            binding.txtDireccion.text.toString(),
            binding.txttelefono.text.toString(),
            distrito
        )
        clienteViewModel.RegistrarClient( cliente, this )
    }
    private fun InitViewModel(){
        clienteViewModel.responseLiveData.observe( this,  Observer {
            if( it != null ){
                Toast.makeText( this, it, Toast.LENGTH_SHORT ).show()
            }else{
                Toast.makeText( this, "Error Server", Toast.LENGTH_SHORT ).show()
            }
        } )
        distritoViewModel.responseString.observe( this, Observer {
            if( it != null ){
                val adapter = ArrayAdapter( this, android.R.layout.simple_spinner_item, it )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spDistritoregistro.adapter = adapter

                binding.spDistritoregistro.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val int = adapter.getPosition( it[position] )
                        nombreDistri = it[ int ]
                        Toast.makeText( this@SignupActivity, nombreDistri, Toast.LENGTH_LONG ).show()
                        BuscarDistrito( nombreDistri!! )
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
        } )
        distritoViewModel.responseDistritoMutableLiveData.observe( this, Observer {
            if( it != null ){
                distrito = it
            }
        } )
    }
    override fun ValiCliente(vali: Boolean) {
        if( vali ){
            Preferences.constantes.saveTelefono( binding.txttelefono.text.toString() )
            startActivity( Intent( this, Signup2Activity::class.java ) )
        }
    }
}