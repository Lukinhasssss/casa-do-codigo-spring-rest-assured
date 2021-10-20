package br.com.lukinhasssss.casa_do_codigo.exceptions

import org.springframework.http.HttpStatus

class AlreadyExistsException(
    val httpStatus: HttpStatus,
    val fieldName: String? = "",
    override val message: String
) : RuntimeException()