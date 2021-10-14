package br.com.lukinhasssss.casa_do_codigo.dto.request

import br.com.lukinhasssss.casa_do_codigo.config.validations.CheckIfExists
import br.com.lukinhasssss.casa_do_codigo.exceptions.NotFoundException
import br.com.lukinhasssss.casa_do_codigo.model.Book
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.Future
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NewBookRequest(

    @field:Size(max = 100)
    @field:NotBlank(message = "Required field")
    @CheckIfExists(domainClass = "Book", fieldName = "title", message = "There is already a book with this title")
    val title: String,

    @field:Size(max = 500)
    @field:NotBlank(message = "Required field")
    val resume: String,

    @field:NotBlank(message = "Required field")
    val summary: String,

    @field:Min(value = 20)
    val price: BigDecimal,

    @field:Min(value = 100)
    val pageQuantity: Int,

    @field:NotBlank(message = "Required field")
    @CheckIfExists(domainClass = "Book", fieldName = "title", message = "There is already a book with this isbn")
    val isbn: String,
    
    @field:Future
    val publicationDate: LocalDate,
    
    @field:NotBlank(message = "Required field")
    val authorName: String,
    
    @field:NotBlank(message = "Required field")
    val category: String

) {

    fun toModel(authorRepository: AuthorRepository, categoryRepository: CategoryRepository): Book {
        val author = authorRepository.findByName(authorName)
            .orElseThrow { NotFoundException(fieldName = "authorName", message = Exception("Author not found")) }

        val category = categoryRepository.findByName(category)
            .orElseThrow { NotFoundException(fieldName = "authorName", message = Exception("Category not found")) }

        return Book(this, author, category)
    }

}
