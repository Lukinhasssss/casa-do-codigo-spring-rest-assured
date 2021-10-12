package br.com.lukinhasssss.casa_do_codigo.repositories

import br.com.lukinhasssss.casa_do_codigo.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BookRepository : JpaRepository<Book, String> {

    fun findByTitle(title: String): Optional<Book>

    fun findByIsbn(isbn: String): Optional<Book>

}