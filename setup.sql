create table if not exists ecommerce_product_category(
    category_id    		   varchar(60)     primary key,
    category_name          varchar(60)     not null,
    created_at             date            not null
);

create table if not exists ecommerce_payment
(
    payment_id          varchar(60)     primary key,
    amount              double precision not null,
    confirmation_number varchar(60)      not null,
    created_at          date             not null,
    payment_mode        varchar(60)      not null,
    payment_status      varchar(60)      not null
);

create table if not exists ecommerce_product
(
    product_id           varchar(60)        primary key,
    created_at           date               not null,
    description          varchar(60)        not null,
    price                double precision   not null,
    sku                  varchar(60)        not null,
    title                varchar(60)        not null,
    category_id  		 varchar(60)        not null,
	FOREIGN KEY (category_id) REFERENCES ecommerce_product_category (category_id)
);

create table if not exists ecommerce_address
(
    address_id     varchar(60) primary key,
    address1       varchar(60) not null,
    address2       varchar(60) not null,
    city           varchar(60) not null,
    created_at     date        not null,
    email          varchar(60) not null,
    phone          varchar(60) not null,
    state          varchar(60) not null,
    zip            varchar(60) not null
);

create table if not exists ecommerce_order
(
    order_id            varchar(60) PRIMARY KEY,
    created_at          timestamp,
    customer_id         varchar(60),
    order_status        varchar(60),
    shipping_charges    double precision,
    shipping_mode       varchar(60),
    sub_total           double precision,
    tax                 double precision,
    title               varchar(60),
    total_amt           double precision,
    payment_id          varchar(60),
    billing_address_id  varchar(60),
    shipping_address_id varchar(60),
    FOREIGN KEY (payment_id) REFERENCES ecommerce_payment (payment_id),
    FOREIGN KEY (billing_address_id) REFERENCES ecommerce_address (address_id),
    FOREIGN KEY (shipping_address_id) REFERENCES ecommerce_address (address_id)
);

create table if not exists ecommerce_order_item
(
    order_id       varchar(60),
    product_id     varchar(60),
    quantity       varchar(60) not null,

    PRIMARY KEY (order_id,product_id),
    FOREIGN KEY (order_id) REFERENCES ecommerce_order (order_id),
    FOREIGN KEY (product_id) REFERENCES ecommerce_product (product_id)
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

create table if not exists ecommerce_token
(
	id				 varchar(60) primary key,
    token       	 varchar(60) not null,
    created_at     	 timestamp not null,
	user_id 		 varchar(60) not null
);

insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1001', current_date, 'GROCERY');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1002', current_date, 'STATIONARY');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1003', current_date, 'ELECTRONICS');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1004', current_date, 'BEAUTY');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1005', current_date, 'HEALTH');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1006', current_date, 'FASHION');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1007', current_date, 'DRUGS');
insert into ecommerce_product_category (category_id, created_at, category_name)
values ('PC1008', current_date, 'FITNESS');

insert into ecommerce_user(id,first_name,last_name,email,password,role,user_status)
values('PK69187538','Pankaj','Kumar','pankajkaalyan@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','SUPER','ACTIVE');
insert into ecommerce_user(id,first_name,last_name,email,password,role,user_status)
values('CE69187538','Chris','Evans','chris@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','ADMIN','ACTIVE');
insert into ecommerce_user(id,first_name,last_name,email,password,role,user_status)
values('MJ69187538','Mike','Joseph','mike@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','MANAGER','ACTIVE');
insert into ecommerce_user(id,first_name,last_name,email,password,role,user_status)
values('SC69187538','Simon','Cook','simon@gmail.com','E10ADC3949BA59ABBE56E057F20F883E','TESTER','ACTIVE');

insert into ecommerce_token(id,token,created_at,user_id)
values('TKN-80660079','abc3c9f7-f42a-4119-a974-6b341b00dae4','2022-12-24 00:00:00','PK69187538');
insert into ecommerce_token(id,token,created_at,user_id)
values('TKN-80660080','abc3c9f7-f42a-4119-a974-6b341b00dae5','2022-12-24 00:00:00','CE69187538');
insert into ecommerce_token(id,token,created_at,user_id)
values('TKN-80660081','abc3c9f7-f42a-4119-a974-6b341b00dae6','2022-12-24 00:00:00','MJ69187538');
insert into ecommerce_token(id,token,created_at,user_id)
values('TKN-80660082','abc3c9f7-f42a-4119-a974-6b341b00dae7','2022-12-24 00:00:00','SC69187538');

insert into ecommerce_product (product_id, created_at, description, price, sku, title,category_id)
values (109, current_date, 'Whey', '1.99', '1001', 'protein','PC1008');

insert into ecommerce_product (product_id, created_at, description, price, sku, title,category_id)
values (108, current_date, 'Orgain', '5.99', '1005', 'protein','PC1008');
