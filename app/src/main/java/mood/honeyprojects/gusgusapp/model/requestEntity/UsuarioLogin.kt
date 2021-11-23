package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class UsuarioLogin(
    @SerializedName("usuario") val usuario: String?,
    @SerializedName("contrasenia") val contrasenia: String?
)