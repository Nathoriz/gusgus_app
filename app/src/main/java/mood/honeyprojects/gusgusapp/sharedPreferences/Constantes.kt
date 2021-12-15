package mood.honeyprojects.gusgusapp.sharedPreferences

import android.content.Context

class Constantes( val context: Context ) {
    //Variables
    val SHARED_NAME = "Mydtb"

    val SHARED_DNI = "SHARED_DNI"
    val SHARED_ADMIN_NAME = "SHARED_ADMIN_NAME"
    val SHARED_CLIENT_NAME = "SHARED_CLIENT_NAME"
    val SHARED_ROL_NAME = "SHARED_ROL_NAME"
    val SHARED_TELEFONO = "SHARED_TELEFONO"
    val SHARED_EXIST = "SHARED_EXIST"
    val SHARED_PRODUCT_ID = "SHARED_PRODUCT_ID"
    val SHARED_TELEFONO_USER = "SHARED_TELEFONO_USER"
    val SHARED_DIRECCION = "SHARED_DIRECCION"
    val SHARED_IDCLIENTE = "SHARED_IDCLIENTE"
    val SHARED_DISTRITO = "SHARED_DISTRITO"
    val SHARED_IDDISTRI = "SHARED_IDDISTRI"
    val SHARED_NOMDISTRI = "SHARED_NOMDISTRI"

    val storage = context.getSharedPreferences( SHARED_NAME, 0 )

    fun saveNomDistri( nombre: String ){
        storage.edit().putString( SHARED_NOMDISTRI, nombre ).apply()
    }
    fun getNomDistri(): String{
        return storage.getString( SHARED_NOMDISTRI, "" )!!
    }
    fun saveIDistri( id: Long ){
        storage.edit().putLong( SHARED_IDDISTRI, id ).apply()
    }
    fun getIDistri(): Long{
        return storage.getLong( SHARED_IDDISTRI, 0L )
    }
    fun saveDistrito( distrito: String ){
        storage.edit().putString( SHARED_DISTRITO, distrito ).apply()
    }
    fun getDistrito(): String{
        return storage.getString( SHARED_DISTRITO, "" )!!
    }
    fun saveIDCliente( id: Long ){
        storage.edit().putLong( SHARED_IDCLIENTE, id ).apply()
    }
    fun getIDCliente(): Long{
        return storage.getLong( SHARED_IDCLIENTE, 0L )
    }
    fun saveDireccion( direccion: String ){
        storage.edit().putString( SHARED_DIRECCION, direccion ).apply()
    }
    fun getDireccion(): String{
        return storage.getString( SHARED_DIRECCION, "" )!!
    }
    fun saveTelefonoUser( telefono: String ){
        storage.edit().putString( SHARED_TELEFONO_USER, telefono ).apply()
    }
    fun getTelefonoUser(): String{
        return storage.getString( SHARED_TELEFONO_USER, "" )!!
    }
    fun saveIdProduct( id: Long ){
        storage.edit().putLong( SHARED_PRODUCT_ID, id ).apply()
    }
    fun getIdProduct(): Long{
        return storage.getLong( SHARED_PRODUCT_ID, 0L )
    }
    fun saveDNI( dni: String ){
        storage.edit().putString( SHARED_DNI, dni ).apply()
    }
    fun getDNI(): String{
        return storage.getString( SHARED_DNI, "" )!!
    }
    fun saveBoolean( exist: Boolean ){
        storage.edit().putBoolean( SHARED_EXIST, exist ).apply()
    }
    fun getBoolean(): Boolean{
        return storage.getBoolean( SHARED_EXIST, false )
    }
    fun saveTelefono( telefono: String ){
        storage.edit().putString( SHARED_TELEFONO, telefono ).apply()
    }
    fun getTelefono(): String{
        return storage.getString( SHARED_TELEFONO, "" )!!
    }

    fun saveAdminName( adminName: String ){
        return storage.edit().putString( SHARED_ADMIN_NAME, adminName ).apply()
    }
    fun getAdminName(): String{
        return storage.getString( SHARED_ADMIN_NAME, "" )!!
    }

    fun saveClientName( name: String ){
        return storage.edit().putString( SHARED_CLIENT_NAME, name ).apply()
    }
    fun getClientName(): String{
        return storage.getString( SHARED_CLIENT_NAME, "" )!!
    }

    fun saveRole( role: String ){
        return storage.edit().putString( SHARED_ROL_NAME, role ).apply()
    }
    fun getRole(): String{
        return storage.getString( SHARED_ROL_NAME, "" )!!
    }
}