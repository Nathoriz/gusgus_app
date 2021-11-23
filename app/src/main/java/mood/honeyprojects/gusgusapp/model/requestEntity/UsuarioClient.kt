package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName

class UsuarioClient(
    @SerializedName("cliente") val cliente: Client?,
    @SerializedName("contrasenia") val contrasenia: String?,
    @SerializedName("usuario") val usuario: String?
)