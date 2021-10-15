package br.com.lukinhasssss.casa_do_codigo.dto.request.category

import br.com.lukinhasssss.casa_do_codigo.exceptions.AlreadyExistsException
import br.com.lukinhasssss.casa_do_codigo.model.Category
import br.com.lukinhasssss.casa_do_codigo.repositories.CategoryRepository
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class NewCategoryRequest(

    @field:NotBlank(message = "Required field")
    @field:Size(max = 50, message = "Name cannot be longer than 50 characters")
    val name: String

) {

    fun toModel(categoryRepository: CategoryRepository): Category {
        if (categoryRepository.findByName(name).isPresent)
            throw AlreadyExistsException(fieldName = "name", message = Exception("There is already a category with this name"))

        return Category(name = name)
    }

}