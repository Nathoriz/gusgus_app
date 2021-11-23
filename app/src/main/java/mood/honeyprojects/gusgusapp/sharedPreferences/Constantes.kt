package mood.honeyprojects.gusgusapp.sharedPreferences

import android.content.Context

class Constantes( val context: Context ) {
    //Variables
    val SHARED_NAME = "Mydtb"

    val SHARED_USER_NAME = "SHARED_USER_NAME"
    val SHARED_APELLIDO = "SHARED_APELLIDO"
    val SHARED_DNI = "SHARED_DNI"
    val SHARED_INDEX = "SHARED_INDEX"
    val SHARED_ADMIN_NAME = "SHARED_ADMIN_NAME"
    val SHARED_CLIENT_NAME = "SHARED_CLIENT_NAME"
    val SHARED_ROL_NAME = "SHARED_ROL_NAME"
    val SHARED_TELEFONO = "SHARED_TELEFONO"
    val SHARED_EXIST = "SHARED_EXIST"

    val storage = context.getSharedPreferences( SHARED_NAME, 0 )

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
        return storage.getBoolean( SHARED_EXIST, false )!!
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