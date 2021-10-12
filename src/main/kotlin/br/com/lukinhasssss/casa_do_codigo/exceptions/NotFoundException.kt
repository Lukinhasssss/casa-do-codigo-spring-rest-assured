package br.com.lukinhasssss.casa_do_codigo.exceptions

class NotFoundException(fieldName: String, message: Throwable) : RuntimeException(fieldName, message)