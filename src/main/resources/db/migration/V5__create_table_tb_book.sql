create table tb_book(
    id varchar(36) unique not null,
    title varchar(100) unique not null,
    resume varchar(500) not null,
    summary text not null,
    price double precision not null,
    page_quantity int not null,
    isbn varchar(30) unique not null,
    publication_date date not null,
    author_id varchar(36) not null,
    category_id varchar(36) not null,
    primary key(id),
    foreign key(author_id) references tb_author(id),
    foreign key(category_id) references tb_category(id)
);