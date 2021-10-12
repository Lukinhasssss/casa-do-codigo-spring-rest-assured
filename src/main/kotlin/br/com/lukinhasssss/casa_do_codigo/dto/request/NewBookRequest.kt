package br.com.lukinhasssss.casa_do_codigo.dto.request

import br.com.lukinhasssss.casa_do_codigo.exceptions.AlreadyExistsException
import br.com.lukinhasssss.casa_do_codigo.exceptions.NotFoundException
import br.com.lukinhasssss.casa_do_codigo.model.Book
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.BookRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import java.math.BigDecimal
import java.time.LocalDate
import javax.validation.constraints.*

data class NewBookRequest(

    @Size(max = 100)
    @NotBlank(message = "Required field")
    val title: String,

    @Size(max = 500)
    @NotBlank(message = "Required field")
    val resume: String,

    @NotBlank(message = "Required field")
    val summary: String,

    @Min(value = 20)
    @NotNull(message = "Required field")
    val price: BigDecimal,

    @Min(value = 100)
    @NotNull(message = "Required field")
    val pageQuantity: Int,

    @NotBlank(message = "Required field")
    val isbn: String,
    
    @Future
    @NotNull(message = "Required field")
    val publicationDate: LocalDate,
    
    @NotBlank(message = "Required field")
    val authorName: String,
    
    @NotBlank(message = "Required field")
    val category: String

) {

    fun toModel(
        authorRepository: AuthorRepository,
        categoryRepository: CategoryRepository,
        bookRepository: BookRepository
    ): Book {
        val author = authorRepository.findByName(authorName)
            .orElseThrow { NotFoundException(fieldName = "authorName", message = Exception("Author not found")) }

        val category = categoryRepository.findByName(category)
            .orElseThrow { NotFoundException(fieldName = "authorName", message = Exception("Category not found")) }

        if (bookRepository.findByTitle(title).isPresent)
            throw AlreadyExistsException(fieldName = "title", message = Exception("There is already a book with this title"))

        if (bookRepository.findByIsbn(isbn).isPresent)
            throw AlreadyExistsException(fieldName = "title", message = Exception("There is already a book with this ISBN"))

        return Book(this, author, category)
    }

}
