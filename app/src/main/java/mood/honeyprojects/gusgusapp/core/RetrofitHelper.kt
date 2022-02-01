package mood.honeyprojects.gusgusapp.core

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit() : Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
        client.addInterceptor( logging )
        // IP_MELISSA = http://192.168.1.4:8080/
        // IP_EMILIO = http://192.168.100.41:8080/
        return Retrofit.Builder().baseUrl("http://192.168.1.4:8080/")
            .client( client.build() )
            .addConverterFactory( GsonConverterFactory.create() )
            .build()
    }
}