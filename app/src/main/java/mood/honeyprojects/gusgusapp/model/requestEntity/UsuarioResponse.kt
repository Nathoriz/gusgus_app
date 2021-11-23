package mood.honeyprojects.gusgusapp.model.requestEntity

import com.google.gson.annotations.SerializedName
import mood.honeyprojects.gusgusapp.model.entity.Usuario

class UsuarioResponse(
    @SerializedName("Usuario") val usuario : Usuario?
)