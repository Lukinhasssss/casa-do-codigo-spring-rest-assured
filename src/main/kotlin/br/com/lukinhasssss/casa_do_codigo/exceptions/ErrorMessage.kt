package br.com.lukinhasssss.casa_do_codigo.exceptions

import com.fasterxml.jackson.annotation.JsonInclude

data class ErrorMessage(

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    val fieldName: String?,
    val message: String

)