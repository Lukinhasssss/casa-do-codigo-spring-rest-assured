package br.com.lukinhasssss.casa_do_codigo.controllers.category

import br.com.lukinhasssss.casa_do_codigo.controllers.author.NewAuthorController
import br.com.lukinhasssss.casa_do_codigo.dto.request.category.NewCategoryRequest
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
@RequestMapping("/api/v1/categories")
class NewCategoryController(
    private val categoryRepository: CategoryRepository
) {

    private val logger = LoggerFactory.getLogger(NewAuthorController::class.java)

    @PostMapping
    fun registerAuthor(@RequestBody @Valid request: NewCategoryRequest): ResponseEntity<Unit> {
        logger.info("Starting POST on route: /categories - Body: $request")

        val category = request.toModel(categoryRepository).let { categoryRepository.save(it) }

        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(category.id).toUri()

        logger.info("Finishing POST on route: /categories")
        return ResponseEntity.created(uri).build()
    }

}