create table tb_customer(
    id varchar(36) unique not null,
    name varchar(15) not null,
    last_name varchar(30) not null,
    email varchar(50) unique not null,
    document varchar(18) unique not null,
    address varchar(100) not null,
    complement varchar(50) not null,
    city varchar(30) not null,
    state varchar(30) not null,
    country varchar(30) not null,
    phone varchar(15) not null,
    zip_code varchar(9) not null,
    primary key(id)
);