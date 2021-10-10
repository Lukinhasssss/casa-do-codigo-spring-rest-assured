package br.com.lukinhasssss.casa_do_codigo.controllers

import br.com.lukinhasssss.casa_do_codigo.dto.request.NewAuthorRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/author")
class NewAuthorController(
    private val authorRepository: AuthorRepository
) {

    fun registerAuthor(@RequestBody @Valid request: NewAuthorRequest): ResponseEntity<Unit> {
        val author = request.toModel()
        authorRepository.save(author)

        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.id).toUri()

        return ResponseEntity.created(uri).build()
    }

}