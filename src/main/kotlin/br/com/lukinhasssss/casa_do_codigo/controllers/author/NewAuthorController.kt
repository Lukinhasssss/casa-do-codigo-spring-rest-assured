package br.com.lukinhasssss.casa_do_codigo.controllers.author

import br.com.lukinhasssss.casa_do_codigo.dto.request.author.NewAuthorRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/authors")
class NewAuthorController(
    private val authorRepository: AuthorRepository
) {

    private val logger = LoggerFactory.getLogger(NewAuthorController::class.java)

    @PostMapping
    fun registerAuthor(@RequestBody @Valid request: NewAuthorRequest): ResponseEntity<Unit> {
        logger.info("Starting POST on route: /authors - Body: $request")

        val author = request.toModel(authorRepository).let { authorRepository.save(it) }

        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.id).toUri()

        logger.info("Finishing POST on route: /authors")
        return ResponseEntity.created(uri).build()
    }

}