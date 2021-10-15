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
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BookDetailsControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @AfterEach
    internal fun tearDown() {
        bookRepository.deleteAll()
        authorRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @Test
    internal fun `should return 200 with book details`() {
        // Arrange
        authorRepository.save(authorRequest.toModel(authorRepository))
        categoryRepository.save(categoryRequest.toModel(categoryRepository))
        val book = bookRepository.save(bookRequest.toModel(authorRepository, categoryRepository))

        // Act - Assert
        Given {
            port(port)
        } When {
            get("/api/v1/books/${book.id}")
        } Then {
            statusCode(200)
            body("", notNullValue())
        }
    }

    @Test
    internal fun `should return 404 with an erro message when book do not exists`() {
        // Arrange
        authorRepository.save(authorRequest.toModel(authorRepository))
        categoryRepository.save(categoryRequest.toModel(categoryRepository))
        bookRepository.save(bookRequest.toModel(authorRepository, categoryRepository))

        // Act - Assert
        Given {
            port(port)
        } When {
            get("/api/v1/books/${UUID.randomUUID()}")
        } Then {
            statusCode(404)
            body("", notNullValue())
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
