package br.com.lukinhasssss.casa_do_codigo.dto.response

import br.com.lukinhasssss.casa_do_codigo.model.Book

data class BookResponse(

    val id: String?,
    val name: String

) {

    constructor(book: Book) : this(
        id = book.id,
        name = book.title
    )

}