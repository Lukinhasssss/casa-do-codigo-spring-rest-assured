package br.com.lukinhasssss.casa_do_codigo.dto.request

import br.com.lukinhasssss.casa_do_codigo.model.Author
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class NewAuthorRequest(

    @field:NotBlank(message = "Campo obrigatório")
    val name: String,

    @field:NotBlank(message = "Campo obrigatório")
    @field:Pattern(regexp = "[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+", message = "Email inválido")
    val email: String,

    @field:NotBlank(message = "Campo obrigatório")
    @field:Size(max = 400)
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