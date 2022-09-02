CREATE TABLE tb_product
(
    id                BIGINT       NOT NULL,
    uri_img           VARCHAR(255) NULL,
    name              VARCHAR(255) NULL,
    `description`     VARCHAR(255) NULL,
    price             DECIMAL      NULL,
    registration_date date         NULL,
    CONSTRAINT pk_tb_product PRIMARY KEY (id)
);