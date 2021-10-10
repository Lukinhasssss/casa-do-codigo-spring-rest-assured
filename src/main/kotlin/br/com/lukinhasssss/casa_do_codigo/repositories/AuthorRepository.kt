package br.com.lukinhasssss.casa_do_codigo.repositories

import br.com.lukinhasssss.casa_do_codigo.model.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, String>