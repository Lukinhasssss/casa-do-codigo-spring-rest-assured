package br.com.lukinhasssss.casa_do_codigo.controllers

import br.com.lukinhasssss.casa_do_codigo.dto.request.NewBookRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.BookRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/books")
class NewBookController(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository,
    private val categoryRepository: CategoryRepository
) {

    private val logger = LoggerFactory.getLogger(NewBookController::class.java)

    @PostMapping
    fun registerBook(@RequestBody @Valid request: NewBookRequest): ResponseEntity<Unit> {
        logger.info("Starting POST on route: /books - Body: $request")
        val book = request.toModel(authorRepository, categoryRepository).let { bookRepository.save(it) }

        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.id).toUri()

        logger.info("Finishing POST on route: /books")
        return ResponseEntity.created(uri).build()
    }

}