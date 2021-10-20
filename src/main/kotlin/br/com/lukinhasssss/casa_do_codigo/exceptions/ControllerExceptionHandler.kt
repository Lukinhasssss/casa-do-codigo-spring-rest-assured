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

        logger.error("Error to make request on route: ${request.requestURI}, Body: $validationError")

        return ResponseEntity.badRequest().body(validationError)
    }

    @ExceptionHandler(value = [AlreadyExistsException::class])
    fun handleResponseStatusException(exception: AlreadyExistsException, request: HttpServletRequest): ResponseEntity<ValidationError> {
        val errorMessage = ErrorMessage(fieldName = exception.fieldName, message = exception.message)
        val validationError = ValidationError(status = exception.httpStatus.value(), path = request.requestURI, messages = listOf(errorMessage))

        logger.error("Error to make request on route: ${request.requestURI}, Body: $validationError")

        return ResponseEntity.badRequest().body(validationError)
    }

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFoundException(exception: NotFoundException, request: HttpServletRequest): ResponseEntity<ValidationError> {
        val errorMessage = ErrorMessage(fieldName = exception.fieldName, message = exception.message)
        val validationError = ValidationError(status = exception.httpStatus.value(), path = request.requestURI, messages = listOf(errorMessage))

        logger.error("Error to make request on route: ${request.requestURI}, Body: $validationError")

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(validationError)
    }

}