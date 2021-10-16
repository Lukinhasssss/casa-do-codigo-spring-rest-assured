package br.com.lukinhasssss.casa_do_codigo.config.validations

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

@Target(FIELD)
@Retention(AnnotationRetention.RUNTIME)
@ConstraintComposition(CompositionType.OR)
@CPF @CNPJ
@Constraint(validatedBy = [])
annotation class Document(
    val message: String = "Invalid document",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
