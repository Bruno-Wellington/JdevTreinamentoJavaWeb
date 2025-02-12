/*Sql de criação do banco de dados*/

CREATE DATABASE cursojsp
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
    
    
/*SQL de criação da tabela model_login no banco de dados*/

CREATE TABLE IF NOT EXISTS public.model_login
(
    login character varying(200) COLLATE pg_catalog."default",
    senha character varying(200) COLLATE pg_catalog."default",
    CONSTRAINT login_unique UNIQUE (login)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.model_login
    OWNER to postgres;
    
/*SQL de INSERT de usuario na tabela model_login*/

INSERT INTO public.model_login(login, senha) VALUES ('admin', 'admin');



