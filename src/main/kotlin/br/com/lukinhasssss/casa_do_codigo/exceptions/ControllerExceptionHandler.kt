package br.com.lukinhasssss.casa_do_codigo.exceptions

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ControllerExceptionHandler {

    private val logger = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ValidationError> {
        val errorMessages = exception.bindingResult.fieldErrors.map { ErrorMessage(it.field, it.defaultMessage.toString()) }
        val validationError = ValidationError(status = HttpStatus.BAD_REQUEST.value(), path = request.requestURI, messages = errorMessages)

        logger.error("Erro ao fazer requisição para o path: ${request.requestURI}, Body: $validationError")

        return ResponseEntity.badRequest().body(validationError)
    }

}