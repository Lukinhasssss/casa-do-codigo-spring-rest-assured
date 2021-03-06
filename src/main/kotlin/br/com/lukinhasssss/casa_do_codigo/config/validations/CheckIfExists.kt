package br.com.lukinhasssss.casa_do_codigo.config.validations

import javax.persistence.EntityManager
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@MustBeDocumented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = [CheckIfExistsValidator::class])
annotation class CheckIfExists(

    val domainClass: String,
    val fieldName: String,
    val message: String = "{domainClass} not found",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []

)

class CheckIfExistsValidator(
    private val em: EntityManager
) : ConstraintValidator<CheckIfExists, Any> {

    private lateinit var domainClass: String
    private lateinit var fieldName: String

    override fun initialize(constraintAnnotation: CheckIfExists) {
        domainClass = constraintAnnotation.domainClass
        fieldName = constraintAnnotation.fieldName
    }

    override fun isValid(value: Any?, context: ConstraintValidatorContext?): Boolean {

        return !em.createQuery("select 1 from $domainClass where $fieldName = :value")
            .setParameter("value", value)
            .resultList.isNullOrEmpty()
    }

}