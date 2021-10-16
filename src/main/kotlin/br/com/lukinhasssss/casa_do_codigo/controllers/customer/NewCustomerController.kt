package br.com.lukinhasssss.casa_do_codigo.controllers.customer

import br.com.lukinhasssss.casa_do_codigo.dto.request.customer.NewCustomerRequest
import br.com.lukinhasssss.casa_do_codigo.repositories.CustomerRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/customers")
class NewCustomerController(
    private val customerRepository: CustomerRepository
) {

    private val logger = LoggerFactory.getLogger(NewCustomerController::class.java)

    @PostMapping
    fun registerCustomer(@RequestBody @Valid request: NewCustomerRequest): ResponseEntity<Unit> {
        logger.info("Starting POST on route: /customers - body: $request")
        val user = request.toModel().let { customerRepository.save(it) }

        val uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.id).toUri()

        logger.info("Finishing POST on route: /customers")
        return ResponseEntity.created(uri).build()
    }

}