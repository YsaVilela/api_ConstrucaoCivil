create table TB_CONSTRUTORA(
	ID  serial primary key,
	CNPJ varchar (14)  not null unique,
	NOME varchar (255) not null,
	TELEFONE varchar (11) not null unique,
	EMAIL varchar (255) not null,
	STATUS boolean not null	
);


create table TB_PROJETO (
	ID serial primary key,
	FK_CONSTRUTORA integer,
		foreign key (FK_CONSTRUTORA)
		references TB_CONSTRUTORA(ID),
	NOME varchar (255) not null,
	ORCAMENTO_MAXIMO numeric (10,2) not null,
	DESCRICAO text
);


create table TB_ESTADO(
	ID serial primary key,
	NOME varchar (25) not null,
	UF char (2) not null,
	REGIAO varchar (25) not null
);


create table TB_CIDADE(
	ID serial primary key,
	FK_ESTADO integer,
		foreign key (FK_ESTADO)
		references TB_ESTADO(ID),
	NOME varchar (255) not null	
);


create table TB_ENDERECO(
	ID serial primary key,
	CEP varchar (8) not null,
	LOGRADOURO varchar (255) not null,
	NUMERO integer not null,
	COMPLEMENTO varchar (100),
	FK_CIDADE integer,
		foreign key (FK_CIDADE)
		references TB_CIDADE(ID),
	FK_PROJETO integer,
		foreign key (FK_PROJETO)
		references TB_PROJETO(ID)
);


create table TB_CARGO (
	ID serial primary key,
	NOME varchar (255) not null unique,
	REMUNERACAO numeric (10,2) not null
);


create table TB_PROFISSIONAL (
	ID serial primary key,
	FK_CONSTRUTORA integer,
		foreign key (FK_CONSTRUTORA)
		references TB_CONSTRUTORA(ID),
	FK_CARGO integer,
		foreign key (FK_CARGO)
		references TB_CARGO(ID),
	CPF varchar (11)  not null unique,
	NOME varchar (255) not null,
	TELEFONE bigint not null unique,
	STATUS boolean not null
);


create table TB_EQUIPE (
	ID serial primary key,
	FK_CONSTRUTORA integer,
		foreign key (FK_CONSTRUTORA)
		references TB_CONSTRUTORA(ID),
	NOME  varchar (100) not null,
	TURNO varchar (50) not null,
	STATUS boolean not null
);


create table TB_PROFISSIONAL_EQUIPE (
	ID serial primary key,
	FK_EQUIPE integer,
		foreign key (FK_EQUIPE)
		references TB_EQUIPE(ID),
	FK_PROFISSIONAL integer,
		foreign key (FK_PROFISSIONAL)
		references TB_PROFISSIONAL(ID),
	unique (FK_EQUIPE,FK_PROFISSIONAL)
);


drop table TB_PROFISSIONAL_EQUIPE,TB_EQUIPE,TB_PROFISSIONAL, TB_CARGO,TB_ENDERECO, TB_CIDADE,TB_ESTADO,TB_PROJETO,TB_CONSTRUTORA;

