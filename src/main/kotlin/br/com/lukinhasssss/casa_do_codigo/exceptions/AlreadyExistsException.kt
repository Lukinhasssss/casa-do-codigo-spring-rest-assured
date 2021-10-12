package br.com.lukinhasssss.casa_do_codigo.exceptions

class AlreadyExistsException(fieldName: String, message: Throwable) : RuntimeException(fieldName, message)