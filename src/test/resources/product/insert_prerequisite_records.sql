create table if not exists ecommerce_product
(
    product_id      varchar(60)     primary key,
    created_at  date             not null,
    description varchar(60)     not null,
    price       double precision not null,
    sku         varchar(60)     not null,
    title       varchar(60)     not null,
    category_id varchar(60)     not null
    );

create table if not exists ecommerce_token
(
	id				 varchar(60) primary key,
    token       	 varchar(60) not null,
    created_at     	 timestamp not null,
	user_id 		 varchar(60) not null
);

create table if not exists ecommerce_user
(
	id				 varchar(60) primary key,
    first_name       varchar(60) not null,
    last_name     	 varchar(60) not null,
    email       	 varchar(60) not null,
	password 		 varchar(60) not null,
	role 			 varchar(60) not null,
	user_status		 varchar(60) not null
);

insert into ecommerce_product (product_id, created_at, description, price, sku, title,category_id)
values (106, '2022-10-17', 'Coke', '5.99', '1006', 'SoftDrink','PC1007');

insert into ecommerce_token(id,token,created_at,user_id)
values('TKN-80660079','abc3c9f7-f42a-4119-a974-6b341b00dae4','2022-12-24','PK69187538');

insert into ecommerce_user(id,first_name,last_name,email,password,role,user_status)
values('PK69187538','Pankaj','Kumar','pankajkaalyan@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','SUPER','ACTIVE');

