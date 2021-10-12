package br.com.lukinhasssss.casa_do_codigo.repositories

import br.com.lukinhasssss.casa_do_codigo.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoryRepository : JpaRepository<Category, String> {

    fun findByName(name: String): Optional<Category>

}