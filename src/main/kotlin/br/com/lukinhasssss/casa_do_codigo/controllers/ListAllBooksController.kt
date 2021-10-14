package br.com.lukinhasssss.casa_do_codigo.controllers

import br.com.lukinhasssss.casa_do_codigo.dto.response.BookResponse
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
        logger.info("Starting the book listing...")
        val books = bookRepository.findAll().map { BookResponse(it) }

        logger.info("Finishing the book listing - body: {}", books)
        return ResponseEntity.ok(books)
    }

}