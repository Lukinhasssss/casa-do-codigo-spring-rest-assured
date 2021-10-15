package br.com.lukinhasssss.casa_do_codigo.dto.request.author

import br.com.lukinhasssss.casa_do_codigo.exceptions.AlreadyExistsException
import br.com.lukinhasssss.casa_do_codigo.model.Author
import br.com.lukinhasssss.casa_do_codigo.repositories.AuthorRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class NewAuthorRequest(

    @field:NotBlank(message = "Required field")
    @field:Size(max = 50, message = "Name cannot be longer than 50 characters")
    val name: String,

    @field:NotBlank(message = "Required field")
    @field:Pattern(regexp = "[a-zA-Z0-9+._%-+]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+", message = "Invalid email")
    @field:Size(max = 50, message = "Email cannot be longer than 50 characters")
    val email: String,

    @field:NotBlank(message = "Required field")
    @field:Size(max = 400, message = "Description cannot be longer than 400 characters")
    val description: String

) {

    fun toModel(authorRepository: AuthorRepository): Author {
        authorRepository.findByEmail(email).let {
            if (it.isPresent)
                throw AlreadyExistsException(fieldName = "email", message = Exception("There is already an author with this email"))
        }

        return Author(
            name = name,
            email = email,
            description = description
        )
    }

}