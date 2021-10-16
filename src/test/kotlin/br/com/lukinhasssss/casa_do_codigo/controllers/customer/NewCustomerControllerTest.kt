package br.com.lukinhasssss.casa_do_codigo.controllers.customer

import br.com.lukinhasssss.casa_do_codigo.dto.request.customer.NewCustomerRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.CustomerRepository
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class NewBookControllerTest {

    @LocalServerPort
    private var port = 0

    @Autowired
    lateinit var customerRepository: CustomerRepository

    @AfterEach
    internal fun tearDown() {
        customerRepository.deleteAll()
    }

    @Test
    internal fun `should return 201 with the correct header location when request is valid`() {
        // Arrange
        val request = newCustomerRequest

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(201)
            header(
                "Location",
                Matchers.matchesPattern("http://localhost:$port/api/v1/customers/([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})")
            )
        }
    }

    @Test
    internal fun `should return 400 when name is not provided`() {
        // Arrange
        val request = newCustomerRequest.copy(name = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when name has more than 15 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(name = "a".repeat(16))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when last name is not provided`() {
        // Arrange
        val request = newCustomerRequest.copy(lastName =  "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when last name has more than 30 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(lastName =  "a".repeat(31))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when email already exists`() {
        // Arrange
        val request = newCustomerRequest.copy(document = "14.535.015/0001-69")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when email has more than 50 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(email = "a".repeat(51))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when document is invalid`() {
        // Arrange
        val request = newCustomerRequest.copy(document = "123.456.789-01")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when document already exists`() {
        // Arrange
        val request = newCustomerRequest.copy(email = "sasuke.uchiha@konoha.com")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when address is not provided`() {
        // Arrange
        val request = newCustomerRequest.copy(address = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when address has more than 100 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(address = "a".repeat(101))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when complement has more than 50 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(complement = "a".repeat(51))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when city is not provided`() {
        // Arrange
        val request = newCustomerRequest.copy(city = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when city has more than 30 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(city = "a".repeat(31))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when state is not provided`() {
        // Arrange
        val request = newCustomerRequest.copy(state = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when state has more than 30 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(state = "a".repeat(31))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when country is not provided`() {
        // Arrange
        val request = newCustomerRequest.copy(country = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when country has more than 30 characteres`() {
        // Arrange
        val request = newCustomerRequest.copy(country = "a".repeat(31))

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when phone is invalid`() {
        // Arrange
        val request = newCustomerRequest.copy(phone = "987654321")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    @Test
    internal fun `should return 400 when zip code is invalid`() {
        // Arrange
        val request = newCustomerRequest.copy(zipCode = "")

        // Act - Assert
        Given {
            port(port)
            contentType("application/json")
            body(request)
        } When {
            post("/api/v1/customers")
        } Then {
            statusCode(400)
        }
    }

    private val newCustomerRequest = NewCustomerRequest(
        name = "Sasuke",
        lastName = "Uchiha",
        email = "sasuke.uchiha@gmail.com",
        document = "620.152.990-09",
        address = "Street 1",
        complement = "",
        city = "São Paulo",
        state = "São Paulo",
        country = "Brasil",
        phone = "(11) 98765-4321",
        zipCode = "32470-000"
    )
}