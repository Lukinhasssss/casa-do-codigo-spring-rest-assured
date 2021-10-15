package br.com.lukinhasssss.casa_do_codigo.model

import br.com.lukinhasssss.casa_do_codigo.dto.request.book.NewBookRequest
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "tb_book")
data class Book(

    @Id @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: String? = "",

    val title: String,

    val resume: String,

    val summary: String,

    val price: BigDecimal,

    val pageQuantity: Int,

    val isbn: String,

    val publicationDate: LocalDate,

    @ManyToOne
    @JoinColumn(name = "author_id")
    val author: Author,

    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category

) {

    constructor(newBookRequest: NewBookRequest, author: Author, category: Category) : this(
        title = newBookRequest.title,
        resume = newBookRequest.resume,
        summary = newBookRequest.summary,
        price = newBookRequest.price,
        pageQuantity = newBookRequest.pageQuantity,
        isbn = newBookRequest.isbn,
        publicationDate = newBookRequest.publicationDate,
        author = author,
        category = category
    )

}
