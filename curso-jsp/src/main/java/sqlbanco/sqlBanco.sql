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



/*Criando campos de id, nome e email na tabela model_login*/

alter table model_login add id serial primary key;

alter table model_login add nome character varying(300);

alter table model_login add email character varying(300);

UPDATE model_login SET nome = '';
UPDATE model_login SET email = '';

/*Tornando todos os campos nao nulos*/
ALTER TABLE model_login ALTER COLUMN nome SET not null;
ALTER TABLE model_login ALTER COLUMN email SET not null;
ALTER TABLE model_login ALTER COLUMN id SET not null;
ALTER TABLE model_login ALTER COLUMN login SET not null;
ALTER TABLE model_login ALTER COLUMN senha SET not null;



/*SQL de criação da tabela atualizado*/

-- Table: public.model_login
-- DROP TABLE IF EXISTS public.model_login;
CREATE TABLE IF NOT EXISTS public.model_login
(
    login character varying(200) COLLATE pg_catalog."default" NOT NULL,
    senha character varying(200) COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL DEFAULT nextval('model_login_id_seq'::regclass),
    nome character varying(300) COLLATE pg_catalog."default" NOT NULL,
    email character varying(300) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT model_login_pkey PRIMARY KEY (id),
    CONSTRAINT login_unique UNIQUE (login)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.model_login
    OWNER to postgres;
    
    
/*INSERT Atualizado*/
INSERT INTO public.model_login(login, senha, nome, email) VALUES ('admin', 'admin','','');

/*Criando campo para validar se o usuario é admin ou nao*/
ALTER TABLE model_login ADD COLUMN useradmin boolean NOT NULL DEFAULT false;

/*Criando campo FK para separar os dados pelo usuario que fez o cadastro ou alteração*/
/*Para essa função criamos um campo usuario_id dentro da tabela e referenciamos ele ao id do usuario da propria tabela*/
ALTER TABLE model_login ADD COLUMN usuario_id bigint NOT NULL DEFAULT 1;
ALTER TABLE model_login ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES model_login (id);
UPDATE model_login SET usuario_id = 2 WHERE id > 12;

/*Criando campo de perfil para os usuarios*/
ALTER TABLE model_login ADD COLUMN perfil character varying(200);
update model_login set perfil = 'ADMIN' where id='1';
update model_login set perfil = 'AUXILIAR' where id='2';
update model_login set perfil = 'AUXILIAR' where perfil IS null;

/*Criando campo de sexo para os usuarios*/
ALTER TABLE model_login ADD COLUMN sexo character varying(200);
UPDATE model_login SET sexo = 'MASCULINO' WHERE sexo IS null;