package br.com.lukinhasssss.casa_do_codigo.dto.request.customer

import br.com.lukinhasssss.casa_do_codigo.config.validations.CheckIfAlreadyExists
import br.com.lukinhasssss.casa_do_codigo.config.validations.Document
import br.com.lukinhasssss.casa_do_codigo.model.Customer
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class NewCustomerRequest(

    @field:NotBlank(message = "Required field")
    @field:Size(max = 15, message = "Name cannot be longer than 15 characters")
    val name: String,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 30, message = "Last name cannot be longer than 30 characters")
    val lastName: String,

    @field:Pattern(regexp = "[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+", message = "Invalid email")
    @field:Size(max = 50, message = "Email cannot be longer than 50 characters")
    @field:CheckIfAlreadyExists(domainClass = "Customer", fieldName = "email", message = "This email already exists")
    val email: String,

    @field:Document
    @field:CheckIfAlreadyExists(domainClass = "Customer", fieldName = "document", message = "There is already a customer with this document")
    val document: String,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 100, message = "Address cannot be longer than 100 characters")
    val address: String,

    @field:Size(max = 50, message = "Complement cannot be longer than 50 characters")
    val complement: String,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 30, message = "City cannot be longer than 30 characters")
    val city: String,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 30, message = "State cannot be longer than 30 characters")
    val state: String,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 30, message = "Country cannot be longer than 30 characters")
    val country: String,

    @field:Pattern(regexp = "^(\\(11\\) [9][0-9]{4}-[0-9]{4})|(\\(1[2-9]\\) [5-9][0-9]{3}-[0-9]{4})|(\\([2-9][1-9]\\) [5-9][0-9]{3}-[0-9]{4})\$", message = "Invalid phone")
    val phone: String,

    @field:Pattern(regexp = "^\\d{5}-\\d{3}\$")
    val zipCode: String

) {

    fun toModel(): Customer {
        return Customer(this)
    }

}