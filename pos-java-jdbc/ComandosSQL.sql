/*Criando a tabela userposjava*/
create table userposjava (
	id bigint not null,
	nome character varying(255),
	email character varying(255),
	constraint user_pk primary key(id)
)

select * from userposjava;


/*Inserindo dados*/
insert into userposjava (id, nome, email)
values (1,'bruno','bruno@gmail.com');

insert into userposjava (id, nome, email)
values (2,'suan','suan@gmail.com');

select * from userposjava;


/*Comando para criar auto increment na tabela userposjava no db - Aula 287*/
CREATE SEQUENCE usersequence
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 7;


/*Alterando a tabela userposjava para inserir o usersequence (Auto increment) - Aula 287*/
ALTER TABLE userposjava ALTER COLUMN id SET DEFAULT NEXTVAL('usersequence'::regclass);


/*Tornando o Id unico na tabela userposjava*/
ALTER TABLE userposjava ADD UNIQUE (id);


/*Criando a tabela Telefones*/
CREATE TABLE telefoneuser (
	id bigint not null,
	numero character varying (255) not null,
	tipo character varying (255) not null,
	usuariopessoa bigint not null,
	constraint telefone_id primary key (id);
)


/*Criando foreign key na tabela telefoneuser*/
ALTER TABLE telefoneuser add FOREIGN KEY (usuariopessoa) REFERENCES userposjava(id);


/*Comando para criar auto increment na tabela telefoneuser no db*/
CREATE SEQUENCE telefonesequence
	INCREMENT 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1;
ALTER TABLE telefonesequence
	OWNER TO postgres;


/*Alterando a tabela telefoneuser para inserir o telefonesequence (Auto increment)*/
ALTER TABLE telefoneuser ALTER COLUMN id SET DEFAULT NEXTVAL('telefonesequence'::regclass);

