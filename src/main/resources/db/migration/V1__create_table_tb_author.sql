create table tb_author(
    id varchar(36) not null,
    name varchar(50) not null,
    email varchar(50) not null,
    description text not null,
    created_at timestamp not null,
    primary key(id)
);