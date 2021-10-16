package br.com.lukinhasssss.casa_do_codigo.repositories

import br.com.lukinhasssss.casa_do_codigo.model.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository : JpaRepository<Customer, String>