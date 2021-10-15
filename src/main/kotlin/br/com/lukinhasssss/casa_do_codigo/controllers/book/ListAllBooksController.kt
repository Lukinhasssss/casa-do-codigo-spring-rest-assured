package br.com.lukinhasssss.casa_do_codigo.controllers.book

import br.com.lukinhasssss.casa_do_codigo.dto.response.book.BookResponse
import br.com.lukinhasssss.casa_do_codigo.repositories.BookRepository
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class ListAllBooksController(
    private val bookRepository: BookRepository
) {

    private val logger = LoggerFactory.getLogger(ListAllBooksController::class.java)

    @GetMapping
    fun listAllBooks(): ResponseEntity<List<BookResponse>> {
        logger.info("Start listing of all books on path: /api/v1/books")
        val books = bookRepository.findAll().map { BookResponse(it) }

        logger.info("End listing of all books on path: /api/v1/books - body: {}", books)
        return ResponseEntity.ok(books)
    }

}