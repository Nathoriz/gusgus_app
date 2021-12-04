package mood.honeyprojects.gusgusapp.listeners

import mood.honeyprojects.gusgusapp.model.entity.Categoria

interface CategoriaListener {
    fun categoriaClicked( categoria: Categoria )
}