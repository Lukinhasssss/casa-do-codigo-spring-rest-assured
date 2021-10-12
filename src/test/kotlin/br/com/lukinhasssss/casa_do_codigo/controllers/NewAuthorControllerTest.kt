package br.com.lukinhasssss.casa_do_codigo.controllers

import br.com.lukinhasssss.casa_do_codigo.dto.request.NewAuthorRequest
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers.*
import org.hamcrest.core.IsEqual
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NewAuthorControllerTest {

    @LocalServerPort
    private var port: Int = 0

    @Test
    internal fun `should return 201 with the correct header location when request is valid`() {
        // Arrange
        val request = NewAuthorRequest(name = "Monkey D. Luffy", email = "luffy@gmail.com", description = "Meat")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(201)
            header("Location", matchesPattern("http://localhost:$port/api/v1/authors/([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"))
        }
    }

    @Test
    internal fun `should return 400 with an error message when name is not provided`() {
        // Arrange
        val request = NewAuthorRequest(name = "", email = "luffy@gmail.com", description = "Meat")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("name"))
            body("messages.get(0).message", IsEqual("Required field"))
        }
    }

    @Test
    internal fun `should return 400 with an error message when name have more than 50 characters`() {
        // Arrange
        val request = NewAuthorRequest(name = "a".repeat(51), email = "luffy@gmail.com", description = "Meat")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("name"))
            body("messages.get(0).message", IsEqual("Name cannot be longer than 50 characters"))
        }
    }

    @Test
    internal fun `should return 400 with an error message when email is not provided or invalid`() {
        // Arrange
        val request = NewAuthorRequest(name = "Monkey D. Luffy", email = "", description = "Meat")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(2))
            body("messages.findAll { it.message == 'Required field' }.size()", IsEqual(1))
            body("messages.findAll { it.message == 'Invalid email' }.size()", IsEqual(1))
        }
    }

    @Test
    internal fun `should return 400 with an error message when email have more than 50 characters`() {
        // Arrange
        val request = NewAuthorRequest(name = "Monkey D. Luffy", email = "a".repeat(51).plus("@gmail.com"), description = "Meat")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("email"))
            body("messages.get(0).message", IsEqual("Email cannot be longer than 50 characters"))
        }
    }

    @Test
    internal fun `should return 400 with an error message when description is not provided`() {
        // Arrange
        val request = NewAuthorRequest(name = "Monkey D. Luffy", email = "luffy@gmail.com", description = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("description"))
            body("messages.get(0).message", IsEqual("Required field"))
        }
    }

    @Test
    internal fun `should return 400 with an error message when description have more than 400 characters`() {
        // Arrange
        val request = NewAuthorRequest(name = "Monkey D. Luffy", email = "luffy@gmail.com", description = "a".repeat(401))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(1))
            body("messages.get(0).fieldName", IsEqual("description"))
            body("messages.get(0).message", IsEqual("Description cannot be longer than 400 characters"))
        }
    }

    @Test
    internal fun `should return 400 with an error message when none field is provided and need to return a list with all errors`() {
        // Arrange
        val request = NewAuthorRequest(name = "", email = "", description = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/authors")
        } Then  {
            statusCode(400)
            body("timestamp", notNullValue())
            body("status", IsEqual(400))
            body("path", IsEqual("/api/v1/authors"))
            body("messages.size()", IsEqual(4))
            body("messages.findAll { it.fieldName == 'name' }.size()", IsEqual(1))
            body("messages.find { it.fieldName == 'name' }", hasValue("Required field"))
            body("messages.findAll { it.fieldName == 'description' }.size()", IsEqual(1))
            body("messages.find { it.fieldName == 'description' }", hasValue("Required field"))
            body("messages.findAll { it.fieldName == 'email' }.size()", IsEqual(2))
        }
    }
}