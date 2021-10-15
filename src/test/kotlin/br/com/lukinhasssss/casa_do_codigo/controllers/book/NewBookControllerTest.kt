package br.com.lukinhasssss.casa_do_codigo.controllers.book

import br.com.lukinhasssss.casa_do_codigo.dto.request.book.NewBookRequest
import br.com.lukinhasssss.casa_do_codigo.model.Author
import br.com.lukinhasssss.casa_do_codigo.model.Category
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.BookRepository
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NewBookControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var bookRepository: BookRepository

    @Autowired
    lateinit var authorRepository: AuthorRepository

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @BeforeEach
    internal fun setUp() {
        authorRepository.save(author)
        categoryRepository.save(category)
    }

    @AfterEach
    internal fun tearDown() {
        bookRepository.deleteAll()
        authorRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @Test
    internal fun `should return 201 with the correct header location when request is valid`() {
        // Arrange
        val request = newBookRequest

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(201)
            header("Location", matchesPattern("http://localhost:$port/api/v1/books/([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"))
        }
    }

    @Test
    internal fun `should return 400 when title is not provided`() {
        // Arrange
        val request = newBookRequest.copy(title = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when title has more than 100 characteres`() {
        // Arrange
        val request = newBookRequest.copy(title = "a".repeat(101))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when title already exists`() {
        // Arrange
        val request = newBookRequest

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when resume is not provided`() {
        // Arrange
        val request = newBookRequest.copy(resume = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when resume has more than 500 characteres`() {
        // Arrange
        val request = newBookRequest.copy(resume = "a".repeat(501))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when summary is not provided`() {
        // Arrange
        val request = newBookRequest.copy(summary = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when price is lower than 20`() {
        // Arrange
        val request = newBookRequest.copy(price = BigDecimal("19.99"))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when page quantity is lower than 100`() {
        // Arrange
        val request = newBookRequest.copy(pageQuantity = 99)

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when isbn is not provided`() {
        // Arrange
        val request = newBookRequest.copy(isbn = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when isbn already exists`() {
        // Arrange
        val request = newBookRequest

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when publication date is not future`() {
        // Arrange
        val request = newBookRequest.copy(pageQuantity = 99)

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when author name is not provided`() {
        // Arrange
        val request = newBookRequest.copy(authorName = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 404 when author is not found`() {
        // Arrange
        val request = newBookRequest.copy(authorName = "Zoro")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(404)
        }
    }

    @Test
    internal fun `should return 400 when category is not provided`() {
        // Arrange
        val request = newBookRequest.copy(category = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 404 when category is not found`() {
        // Arrange
        val request = newBookRequest.copy(category = "Book")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/books")
        } Then {
            statusCode(404)
        }
    }

    private var newBookRequest = NewBookRequest(
        "Book",
        "Resume of the Book",
        "Summary of the Book",
        BigDecimal("20.00"),
        120,
        "ISBN",
        LocalDate.MAX,
        "Mugiwara",
        "Category"
    )

    private val author = Author(
        UUID.randomUUID().toString(),
        "Mugiwara",
        "mugiwara@gmail.com",
        "Description of Mugiwara",
        LocalDateTime.now()
    )

    private val category = Category(UUID.randomUUID().toString(), "Category")
}