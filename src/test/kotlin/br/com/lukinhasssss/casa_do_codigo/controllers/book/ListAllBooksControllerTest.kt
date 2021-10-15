package br.com.lukinhasssss.casa_do_codigo.controllers.book

import br.com.lukinhasssss.casa_do_codigo.dto.request.author.NewAuthorRequest
import br.com.lukinhasssss.casa_do_codigo.dto.request.book.NewBookRequest
import br.com.lukinhasssss.casa_do_codigo.dto.request.category.NewCategoryRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.BookRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ListAllBooksControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @Test
    internal fun `should return 200 with a list of all books`() {
        // Arrange
        authorRepository.save(authorRequest.toModel(authorRepository))
        categoryRepository.save(categoryRequest.toModel(categoryRepository))
        bookRepository.save(bookRequest.toModel(authorRepository, categoryRepository))
        bookRepository.save(bookRequest.toModel(authorRepository, categoryRepository).copy(title = "Book 2", isbn = "987-654-321"))

        // Act - Assert
        Given {
            port(port)
        } When {
            get("/api/v1/books")
        } Then {
            statusCode(200)
            body("size()", IsEqual(2))
            body("get(0).id", notNullValue())
            body("get(0).name", notNullValue())
            body("get(1).id", notNullValue())
            body("get(1).name", notNullValue())
        }
    }

    private val authorRequest = NewAuthorRequest(name = "Monkey D. Luffy", email = "luffy@gmail.com", description = "Mugiwara")
    private val categoryRequest = NewCategoryRequest(name = "Programming")
    private val bookRequest = NewBookRequest(
        title = "Book 1",
        resume = "Resume of Book 1",
        summary = "Summary of Book 1",
        price = BigDecimal("279.99"),
        pageQuantity = 8001,
        isbn = "123-456-789",
        publicationDate = LocalDate.MAX,
        authorName = authorRequest.name,
        category = categoryRequest.name
    )
}
