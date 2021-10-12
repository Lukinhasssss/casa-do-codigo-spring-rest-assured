package br.com.lukinhasssss.casa_do_codigo.controllers

import br.com.lukinhasssss.casa_do_codigo.dto.request.NewCategoryRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NewCategoryControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    @BeforeEach
    internal fun setUp() { categoryRepository.deleteAll() }

    @Test
    internal fun `should return 201 with the correct header location when request is valid`() {
        val request = NewCategoryRequest(name = "Node")

        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/categories")
        } Then {
            statusCode(201)
            header("Location", matchesPattern("http://localhost:$port/api/v1/categories/([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"))
        }
    }

    @Test
    internal fun `should return 400 with and error message when name is not provided`() {
        val request = NewCategoryRequest(name = "")

        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/categories")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/categories"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("name"))
            body("messages.get(0).message", IsEqual("Required field"))
        }
    }

    @Test
    internal fun `should return 400 with and error message when name has more than 50 characteres`() {
        val request = NewCategoryRequest(name = "a".repeat(51))

        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/categories")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/categories"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("name"))
            body("messages.get(0).message", IsEqual("Name cannot be longer than 50 characters"))
        }
    }

    @Test
    internal fun `should return 400 with and error message when already exists a category with name provided`() {
        val request = NewCategoryRequest(name = "Android")

        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/categories")
            post("/api/v1/categories")
        } Then {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/categories"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("name"))
            body("messages.get(0).message", IsEqual("There is already a category with this name"))
        }
    }
}