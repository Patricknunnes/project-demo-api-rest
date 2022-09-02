CREATE TABLE tb_user
(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_tb_user PRIMARY KEY (username)
);

CREATE TABLE tb_users_roles
(
    id       BIGINT       NOT NULL,
    username VARCHAR(255) NOT NULL
);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT fk_tbuserol_on_role FOREIGN KEY (id) REFERENCES tb_role (id);

ALTER TABLE tb_users_roles
    ADD CONSTRAINT fk_tbuserol_on_user FOREIGN KEY (username) REFERENCES tb_user (username);
