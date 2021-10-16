package br.com.lukinhasssss.casa_do_codigo.model

import br.com.lukinhasssss.casa_do_codigo.dto.request.customer.NewCustomerRequest
import org.hibernate.annotations.GenericGenerator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tb_customer")
data class Customer(

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",

    val name: String,

    val lastName: String,

    val email: String,

    val document: String,

    val address: String,

    val complement: String,

    val city: String,

    val state: String,

    val country: String,

    val phone: String,

    val zipCode: String

) {

    constructor(newCustomerRequest: NewCustomerRequest) : this(
        name = newCustomerRequest.name,
        lastName = newCustomerRequest.lastName,
        email = newCustomerRequest.email,
        document = newCustomerRequest.document,
        address = newCustomerRequest.address,
        complement = newCustomerRequest.complement,
        city = newCustomerRequest.city,
        state = newCustomerRequest.state,
        country = newCustomerRequest.country,
        phone = newCustomerRequest.phone,
        zipCode = newCustomerRequest.zipCode
    )

}