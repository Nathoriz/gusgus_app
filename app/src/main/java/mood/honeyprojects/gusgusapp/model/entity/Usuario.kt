package mood.honeyprojects.gusgusapp.model.entity

import com.google.gson.annotations.SerializedName

class Usuario(
        @SerializedName("id" ) val id: Long?,
        @SerializedName("contrasenia" ) val contrasenia: String?,
        @SerializedName("usuario" ) val usuario: String?,
        @SerializedName("admin" ) val admin: Admin?,
        @SerializedName("cliente" ) val cliente: Cliente?,
        @SerializedName("rol" ) val rol: Rol?
)
