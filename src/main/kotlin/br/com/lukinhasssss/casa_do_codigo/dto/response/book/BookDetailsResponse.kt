package br.com.lukinhasssss.casa_do_codigo.dto.response.book

import br.com.lukinhasssss.casa_do_codigo.model.Book
import java.math.BigDecimal

data class BookDetailsResponse(

    val title: String,
    val resume: String,
    val summary: String,
    val price: BigDecimal,
    val pageQuantity: Int,
    val isbn: String,
    val authorName: String,
    val authorDescription: String

) {

    constructor(book: Book) : this(
        title = book.title,
        resume = book.resume,
        summary = book.summary,
        price = book.price,
        pageQuantity = book.pageQuantity,
        isbn = book.isbn,
        authorName = book.author.name,
        authorDescription = book.author.description
    )

}