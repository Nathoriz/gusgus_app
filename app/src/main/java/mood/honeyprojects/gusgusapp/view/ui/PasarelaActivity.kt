package mood.honeyprojects.gusgusapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.synap.pay.SynapPayButton
import com.synap.pay.handler.payment.SynapAuthorizeHandler
import com.synap.pay.model.payment.*
import com.synap.pay.model.payment.response.SynapAuthorizeResponse
import com.synap.pay.model.security.SynapAuthenticator
import com.synap.pay.theming.SynapDarkTheme
import mood.honeyprojects.gusgusapp.R
import mood.honeyprojects.gusgusapp.databinding.ActivityPasarelaBinding
import java.lang.Exception
import java.lang.StringBuilder
import java.security.MessageDigest

class PasarelaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasarelaBinding
    private lateinit var paymentWidget: SynapPayButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasarelaBinding.inflate( layoutInflater )
        setContentView( binding.root )
        supportActionBar?.hide()
        startPayment()
        Listener()
    }
    private fun Listener(){
        binding.btnpagar.setOnClickListener {
            paymentWidget.pay()
        }
    }
    private fun startPayment(){
        //binding.ivpresentacion.visibility = View.GONE
        binding.frsinapsysform.visibility = View.VISIBLE

        //binding.btniniciar.visibility = View.GONE
        binding.btnpagar.visibility = View.VISIBLE

        paymentWidget = SynapPayButton.create( binding.frsinapsysform )
        val theme = SynapDarkTheme()
        SynapPayButton.setTheme( theme )

        SynapPayButton.setEnvironment( SynapPayButton.Environment.SANDBOX )

        val transaction = this.buildTransaction()
        val authenticator = this.buildAuthenticator(transaction)

        SynapPayButton.setListener { event ->
            when (event) {
                SynapPayButton.Events.START_PAY -> {
                    binding.btnpagar.visibility = View.GONE

                }
                SynapPayButton.Events.INVALID_CARD_FORM -> {
                    binding.btnpagar.visibility = View.VISIBLE
                }
                SynapPayButton.Events.VALID_CARD_FORM -> {}
            }
        }

        paymentWidget.configure( // Seteo de autenticación de seguridad y transacción
            authenticator,
            transaction,  // Manejo de la respuesta
            object : SynapAuthorizeHandler {
                override fun success( response: SynapAuthorizeResponse) {
                    Looper.prepare()
                    val resultAccepted = response.result.accepted
                    val resultMessage = response.result.message
                    if (resultAccepted) {
                        // Agregue el código según la experiencia del cliente para la autorización
                        showMessage(resultMessage)
                    } else {
                        // Agregue el código según la experiencia del cliente para la denegación
                        showMessage(resultMessage)
                    }
                    Looper.loop()
                }

                override fun failed(response: SynapAuthorizeResponse) {
                    Looper.prepare()
                    val messageText = response.message.text
                    // Agregue el código de la experiencia que desee visualizar en un error
                    showMessage(messageText)
                    Looper.loop()
                }
            }
        )

        binding.btnpagar.visibility = View.VISIBLE
    }
    private fun buildTransaction(): SynapTransaction {
        // Genere el número de orden, este es solo un ejemplo
        // Genere el número de orden, este es solo un ejemplo
        val number = System.currentTimeMillis().toString()

        // Seteo de los datos de transacción
        // Referencie al objeto país

        // Seteo de los datos de transacción
        // Referencie al objeto país
        val country = SynapCountry()
        // Seteo del código de país
        // Seteo del código de país
        country.code = "PER"

        // Referencie al objeto moneda

        // Referencie al objeto moneda
        val currency = SynapCurrency()
        // Seteo del código de moneda
        // Seteo del código de moneda
        currency.code = "PEN"

        //Seteo del monto

        //Seteo del monto
        val amount = "1.00"

        // Referencie al objeto cliente

        // Referencie al objeto cliente
        val customer = SynapPerson()
        // Seteo del cliente
        // Seteo del cliente
        customer.name = "Javier"
        customer.lastName = "Pérez"

        // Referencie al objeto dirección del cliente

        // Referencie al objeto dirección del cliente
        val address = SynapAddress()
        // Seteo del pais (country), niveles de ubicación geográfica (levels), dirección (line1 y line2) y código postal (zip)
        // Seteo del pais (country), niveles de ubicación geográfica (levels), dirección (line1 y line2) y código postal (zip)
        address.country = "PER"
        address.levels = ArrayList()
        address.levels.add("150000")
        address.levels.add("150100")
        address.levels.add("150101")
        address.line1 = "Ca Carlos Ferreyros 180"
        address.zip = "15036"
        customer.address = address

        // Seteo del email y teléfono

        // Seteo del email y teléfono
        customer.email = "javier.perez@synapsolutions.com"
        customer.phone = "999888777"

        // Referencie al objeto documento del cliente

        // Referencie al objeto documento del cliente
        val document = SynapDocument()
        // Seteo del tipo y número de documento
        // Seteo del tipo y número de documento
        document.type = "DNI"
        document.number = "44556677"
        customer.document = document

        // Seteo de los datos de envío

        // Seteo de los datos de envío
        // Seteo de los datos de facturación
        // Seteo de los datos de facturación

        // Referencie al objeto producto

        // Referencie al objeto producto
        val productItem = SynapProduct()
        // Seteo de los datos de producto
        // Seteo de los datos de producto
        productItem.code = "123"
        productItem.name = "Llavero"
        productItem.quantity = "1"
        productItem.unitAmount = "1.00"
        productItem.amount = "1.00"

        // Referencie al objeto lista de producto

        // Referencie al objeto lista de producto
        val products: MutableList<SynapProduct> = ArrayList()
        // Seteo de los datos de lista de producto
        // Seteo de los datos de lista de producto
        products.add(productItem)

        // Referencie al objeto metadata

        // Referencie al objeto metadata
        val metadataItem = SynapMetadata()
        // Seteo de los datos de metadata
        // Seteo de los datos de metadata
        metadataItem.name = "nombre1"
        metadataItem.value = "valor1"

        // Referencie al objeto lista de metadata

        // Referencie al objeto lista de metadata
        val metadataList: MutableList<SynapMetadata> = ArrayList()
        // Seteo de los datos de lista de metadata
        // Seteo de los datos de lista de metadata
        metadataList.add(metadataItem)

        // Referencie al objeto orden

        // Referencie al objeto orden
        val order = SynapOrder()
        // Seteo de los datos de orden
        // Seteo de los datos de orden
        order.number = number
        order.amount = amount
        order.country = country
        order.currency = currency
        order.products = products
        order.customer = customer
        order.shipping = customer
        order.billing = customer
        order.metadata = metadataList

        // Referencie al objeto configuración

        // Referencie al objeto configuración
        val settings = SynapSettings()
        // Seteo de los datos de configuración
        // Seteo de los datos de configuración
        settings.brands = listOf("VISA", "MSCD", "AMEX", "DINC")
        settings.language = "es_PE"
        settings.businessService = "MOB"

        // Referencie al objeto transacción

        // Referencie al objeto transacción
        val transaction = SynapTransaction()
        // Seteo de los datos de transacción
        // Seteo de los datos de transacción
        transaction.order = order
        transaction.settings = settings

        // Feature Card-Storage (Recordar Tarjeta)

        // Feature Card-Storage (Recordar Tarjeta)
        val features = SynapFeatures()
        val cardStorage = SynapCardStorage()

        // Omitir setUserIdentifier, si se trata de usuario anónimo

        // Omitir setUserIdentifier, si se trata de usuario anónimo
        cardStorage.userIdentifier = "javier.perez@synapsolutions.com"

        features.cardStorage = cardStorage
        transaction.features = features

        return transaction
    }
    private fun buildAuthenticator( transaction: SynapTransaction  ): SynapAuthenticator {
        val apiKey = "ab254a10-ddc2-4d84-8f31-d3fab9d49520"

        // La signatureKey y la función de generación de firma debe usarse e implementarse en el servidor del comercio utilizando la función criptográfica SHA-512
        // solo con propósito de demostrar la funcionalidad, se implementará en el ejemplo
        // (bajo ninguna circunstancia debe exponerse la signatureKey y la función de firma desde la aplicación porque compromete la seguridad)

        // La signatureKey y la función de generación de firma debe usarse e implementarse en el servidor del comercio utilizando la función criptográfica SHA-512
        // solo con propósito de demostrar la funcionalidad, se implementará en el ejemplo
        // (bajo ninguna circunstancia debe exponerse la signatureKey y la función de firma desde la aplicación porque compromete la seguridad)
        val signatureKey = "eDpehY%YPYgsoludCSZhu*WLdmKBWfAo"

        val signature = generateSignature(transaction, apiKey, signatureKey)

        // El campo onBehalf es opcional y se usa cuando un comercio agrupa otros sub comercios
        // la conexión con cada sub comercio se realiza con las credenciales del comercio agrupador
        // y enviando el identificador del sub comercio en el campo onBehalf
        //String onBehalf="cf747220-b471-4439-9130-d086d4ca83d4";

        // Referencie el objeto de autenticación

        // El campo onBehalf es opcional y se usa cuando un comercio agrupa otros sub comercios
        // la conexión con cada sub comercio se realiza con las credenciales del comercio agrupador
        // y enviando el identificador del sub comercio en el campo onBehalf
        //String onBehalf="cf747220-b471-4439-9130-d086d4ca83d4";

        // Referencie el objeto de autenticación
        val authenticator = SynapAuthenticator()

        // Seteo de identificador del comercio (apiKey)

        // Seteo de identificador del comercio (apiKey)
        authenticator.identifier = apiKey

        // Seteo de firma, que permite verificar la integridad de la transacción

        // Seteo de firma, que permite verificar la integridad de la transacción
        authenticator.signature = signature

        // Seteo de identificador de sub comercio (solo si es un subcomercio)
        //authenticator.setOnBehalf(onBehalf);


        // Seteo de identificador de sub comercio (solo si es un subcomercio)
        //authenticator.setOnBehalf(onBehalf);
        return authenticator
    }
    private fun showMessage( message: String ){
        val builder1 = AlertDialog.Builder( this )
        builder1.setMessage( message )
        builder1.setCancelable( true )

        builder1.setPositiveButton(
            "OK"
        ) { dialog, id -> // Finaliza el intento de pago y regresa al inicio, el comercio define la experiencia del cliente
            val looper = Handler(applicationContext.mainLooper)
            looper.post {
                binding.frsinapsysform.visibility = View.INVISIBLE
                //binding.ivpresentacion.visibility = View.VISIBLE
                binding.btnpagar.visibility = View.VISIBLE
                //binding.btniniciar.visibility = View.VISIBLE
            }
            dialog.cancel()
        }

        val alert11 = builder1.create()
        alert11.show()
    }
    private fun generateSignature(transaction: SynapTransaction, apiKey: String, signatureKey: String): String {
        val orderNumber = transaction.order.number
        val currencyCode = transaction.order.currency.code
        val amount = transaction.order.amount

        val rawSignature = apiKey + orderNumber + currencyCode + amount + signatureKey
        return sha512Hex( rawSignature )
    }
    private fun sha512Hex( value: String ): String{
        val sb = StringBuilder()
        try {
            val md = MessageDigest.getInstance("SHA-512")
            val bytes = md.digest(value.toByteArray( charset("UTF-8") ))
            for (i in bytes.indices) {
                sb.append(Integer.toString((bytes[i] + 0xff) + 0x100, 16).substring(1))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return sb.toString()
    }
}