package mood.honeyprojects.gusgusapp.sharedPreferences

import android.app.Application

class Preferences: Application() {
    companion object{
        lateinit var constantes: Constantes
    }

    override fun onCreate() {
        super.onCreate()
        constantes = Constantes( applicationContext )
    }
}