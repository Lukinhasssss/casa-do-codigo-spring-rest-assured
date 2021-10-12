package br.com.lukinhasssss.casa_do_codigo.repositories

import br.com.lukinhasssss.casa_do_codigo.model.Author
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface AuthorRepository : JpaRepository<Author, String> {

    fun findByEmail(email: String): Optional<Author>

}