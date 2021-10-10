package br.com.lukinhasssss.casa_do_codigo.dto.request

import br.com.lukinhasssss.casa_do_codigo.model.Author
import javax.validation.constraints.NotBlank

data class NewAuthorRequest(

    @NotBlank(message = "Campo obrigatório")
    val name: String,

    @NotBlank(message = "Campo obrigatório")
    val email: String,

    @NotBlank(message = "Campo obrigatório")
    val description: String

) {

    fun toModel(): Author {
        return Author(
            name = name,
            email = email,
            description = description
        )
    }

}