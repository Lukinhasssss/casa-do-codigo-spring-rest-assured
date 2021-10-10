package br.com.lukinhasssss.casa_do_codigo.exceptions

import java.time.LocalDateTime

data class ValidationError(

    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val path: String,
    val messages: List<ErrorMessage> = listOf()

)