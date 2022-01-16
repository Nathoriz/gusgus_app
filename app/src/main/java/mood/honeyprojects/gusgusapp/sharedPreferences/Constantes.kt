package mood.honeyprojects.gusgusapp.sharedPreferences

import android.content.Context

class Constantes( val context: Context ) {
    //Variables
    val SHARED_NAME = "Mydtb"

    val SHARED_ADMIN_NAME = "SHARED_ADMIN_NAME"
    val SHARED_CLIENT_WHOLENAME = "SHARED_CLIENT_WHOLENAME"
    val SHARED_CLIENT_NAME = "SHARED_CLIENT_NAME"
    val SHARED_CLIENT_LASTNAME = "SHARED_CLIENT_LASTNAME"
    val SHARED_ROL_NAME = "SHARED_ROL_NAME"
    val SHARED_TELEFONO = "SHARED_TELEFONO"
    val SHARED_EXIST = "SHARED_EXIST"
    val SHARED_PRODUCT_ID = "SHARED_PRODUCT_ID"
    val SHARED_TELEFONO_USER = "SHARED_TELEFONO_USER"
    val SHARED_DIRECCION = "SHARED_DIRECCION"
    val SHARED_IDCLIENTE = "SHARED_IDCLIENTE"
    val SHARED_IDENTREGAA = "SHARED_IDENTREGAA"

    val storage = context.getSharedPreferences( SHARED_NAME, 0 )
    fun saveIdEntrega( id: Long ){
        storage.edit().putLong( SHARED_IDENTREGAA, id ).apply()
    }
    fun getIdEntrega(): Long{
        return storage.getLong( SHARED_IDENTREGAA, 0L )
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

    fun saveClientWholeName( name: String ){
        return storage.edit().putString( SHARED_CLIENT_WHOLENAME, name ).apply()
    }
    fun getClientWholeName(): String{
        return storage.getString( SHARED_CLIENT_WHOLENAME, "" )!!
    }

    fun saveClientName( name: String ){
        return storage.edit().putString( SHARED_CLIENT_NAME, name ).apply()
    }
    fun getClientName(): String{
        return storage.getString( SHARED_CLIENT_NAME, "" )!!
    }

    fun saveClientLastname( lastname: String ){
        return storage.edit().putString( SHARED_CLIENT_LASTNAME, lastname ).apply()
    }
    fun getClientLastname(): String{
        return storage.getString( SHARED_CLIENT_LASTNAME, "" )!!
    }

    fun saveRole( role: String ){
        return storage.edit().putString( SHARED_ROL_NAME, role ).apply()
    }
    fun getRole(): String{
        return storage.getString( SHARED_ROL_NAME, "" )!!
    }
}