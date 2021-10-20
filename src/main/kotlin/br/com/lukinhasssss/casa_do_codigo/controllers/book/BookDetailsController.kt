package br.com.lukinhasssss.casa_do_codigo.controllers.book

import br.com.lukinhasssss.casa_do_codigo.dto.response.book.BookDetailsResponse
import br.com.lukinhasssss.casa_do_codigo.exceptions.NotFoundException
import br.com.lukinhasssss.casa_do_codigo.repositories.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class BookDetailsController(
    private val bookRepository: BookRepository
) {

    private val logger = LoggerFactory.getLogger(BookDetailsController::class.java)

    @GetMapping("/{bookId}")
    fun bookDetails(@PathVariable bookId: String): ResponseEntity<BookDetailsResponse> {
        logger.info("Start of book details listing on path: /api/v1/books/$bookId")
        val book = bookRepository.findById(bookId).orElseThrow { NotFoundException(httpStatus = HttpStatus.NOT_FOUND, message = "Book not found: $bookId") }

        logger.info("End of book details listing on path: /api/v1/books/$bookId - body: {}", BookDetailsResponse(book))
        return ResponseEntity.ok(BookDetailsResponse(book))
    }

}