--- CREACION ---

create table usuario
( nickname varchar(15),
 email varchar(30),
 nombre varchar(30),
 contraseña varchar(20),
 primary key(nickname)
);

create table canciones
( id varchar (7),
 nombre varchar(30),
 autor varchar(30),
 genero varchar(20),
  primary key(id)
);

create table playlist
( id SERIAL,
 nombre varchar(30),
  primary key(id)
 );

create table tiene
( usuario varchar (15),
 id_playlist int,
  primary key(usuario,id_playlist),
 foreign key (usuario) references usuario(nickname),
 foreign key (id_playlist) references playlist(id)
 );

create table contiene
( id_playlist int,
 id_cancion varchar(7),
  primary key(id_cancion,id_playlist),
  foreign key (id_cancion) references canciones(id),
 foreign key (id_playlist) references playlist(id)
);


-- INSERCION --

insert into usuario values('santiagom','sm@hotmail.com','Santiago','123');
insert into usuario values('jcllanos', 'jc@hotmail.com','Juan Camilo', '321');
insert into usuario values('camilom', 'cm@hotmail.com','Camilo', '213');
insert into usuario values('jfperez', 'jfp@hotmail.com','Juan F', '231');
insert into usuario values('tonystark', 'ts@hotmail.com','Tony Stark', '312');

--select *
--from usuario ;

insert into canciones values('009','Save me','Queen','Rock');
insert into canciones values('000', 'We will R', 'Queen','Rock');
insert into canciones values('001', 'Mi muerte', 'Canserbero','Rap');
insert into canciones values('002','Aceptas','Canserbero','Rap');
insert into canciones values('003', 'Requiem', 'Mozart','Clasica');
insert into canciones values('004', 'Don Giovanni', 'Mozart','Clasica');
insert into canciones values('005','Solita','Sech','Regueton');
insert into canciones values('006', 'La discusion', 'Sech','Regueton');
insert into canciones values('007', 'Halo', 'Beyonce','Pop');
insert into canciones values('008', 'Secreto', 'Anuel','Trap');
insert into canciones values('010', 'Humble', 'Kendrick L','Trap');
insert into canciones values('020', 'Vida', 'Canserbero','Rap');
insert into canciones values('050', 'Vida', 'Canser','Rap');
insert into canciones values('030', 'CachaVSWos', 'FMS','Rap');

select * 
from canciones;

insert into playlist(nombre) values('Rock & Metal');
insert into playlist(nombre)  values('Rap Real');
insert into playlist(nombre)  values('Clasica por gusto');
insert into playlist(nombre)  values('Perreo');
insert into playlist(nombre)  values('Preferido');
insert into playlist(nombre)  values('Prueba');
insert into playlist(nombre)  values('Prueba 2');

select * 
from playlist;

insert into contiene values (1,'009');
insert into contiene values (1,'000');
insert into contiene values (2,'001');
insert into contiene values (2,'002');
insert into contiene values (2,'020');
insert into contiene values (2,'010');
insert into contiene values (3,'003');
insert into contiene values (3,'004');
insert into contiene values (4,'005');
insert into contiene values (4,'006');
insert into contiene values (5,'007');
insert into contiene values (5,'000');
insert into contiene values (12,'001');
insert into contiene values (13,'000');

select * 
from contiene;

insert into tiene values ('jcllanos',2);
insert into tiene values ('jcllanos',4);
insert into tiene values ('santiagom',1);
insert into tiene values ('santiagom',3);
insert into tiene values ('camilom',1);
insert into tiene values ('camilom',5);
insert into tiene values ('camilom',12);
insert into tiene values ('camilom',13);

select * 
from tiene;


/*
-- LECTURAS --

<<<<<<< HEAD
select*
from playlist
where nombre = 'Hola';
=======
-- LECTURAS --
>>>>>>> 339f5aa50b0b58bfc9fc7fafdc3f789c0368d766

select usuario,id_playlist
from tiene join usuario 
on tiene.usuario = usuario.nickname;

select *
from contiene join 
(select usuario,id_playlist
from tiene join usuario 
on tiene.usuario = usuario.nickname) as A
on contiene.id_playlist = A.id_playlist
;

select * 
from contiene join tiene
on contiene.id_playlist = tiene.id_playlist;
<<<<<<< HEAD
*/

--- FUNCIONES ---

-- 1) Crear un playlist 
insert into playlist(nombre) values( nombre_ingresado_usuario ) 		-- crear playlist
insert into tiene values(nickname,id_playlist)					-- asociarlo a usuario
	-- EJ :
	select max(id)
	from playlist ;
	insert into playlist(nombre) values ('Salsa');
	--delete from playlist where nombre = 'Salsa';
	insert into tiene values('jfperez',(select max(id) from playlist));
	select * from tiene ;
	-- Aca se hace desde java porque para sacar el id del playlist y tomarlo
	-- como valor en pgadmin es complejo.
							 
-- 2) Buscar playlist y/o canciones -- 
create view cancionesYplaylist_usuario as 
select usuario,nombre as playlist,cancion,autor,genero
from playlist join (
select usuario,nombre as cancion, autor, genero, id_playlist,id_cancion
from canciones join (
select contiene.id_playlist,id_cancion,usuario 
from contiene join tiene
on contiene.id_playlist = tiene.id_playlist) as A
on canciones.id = A.id_cancion
order by usuario) as B
on playlist.id = B.id_playlist
order by usuario,playlist;
										
										
select * from cancionesYplaylist_usuario where usuario = 'jcllanos';										
	
select nombre,autor,genero	         -- buscar en todas las canciones
from canciones 
where nombre = nombre_ing_usuario;
							 
select cancion,autor,genero			-- buscar playlist y canciones que contiene el playlist
from cancionesYplaylist_usuario
where playlist = nombre_ing_usuario;							 
						 							 
	-- EJ :
							 
		-- Por cancion :
		select nombre,autor,genero
		from canciones 
		where nombre = 'Vida';
		-- Por playlist :
		select cancion,autor,genero
		from cancionesYplaylist_usuario
		where playlist = 'Rap Real';

--drop view cancionesYplaylist_usuario;

-- 3) Añadir cancion a un playlist 
insert into contiene values (id_playlist_ing_usuario , id_cancion_ing_usuario);
	-- Ej:
		insert into contiene values (1, 001)

-- 4) Eliminar cancion de un playlist 
delete from contiene
where id_cancion = id_cancion_ing_usuario
and id_playlist = id_playlist_ing_usuario ;
	-- Ej
		delete from contiene
		where id_cancion = '001'
		and id_playlist = '1' ;

-- 5) Recomendaciones 
	  select nombre, autor, canciones.genero 
	  from canciones join ( select genero from cancionesYplaylist_usuario 
	  where usuario = usuario_ing and playlist = nombre_playlist_ing 
	  group by genero) as A 
	  on canciones.genero = A.genero;
							 
	-- Ej 
          /*					    
	  select nombre, autor, canciones.genero 
	  from canciones join ( select genero from cancionesYplaylist_usuario 
	  where usuario = 'jcllanos' and playlist = 'Rap Real' 
	  group by genero) as A 
	  on canciones.genero = A.genero;*/
      
	  select * 						
      from (select nombre, autor, canciones.genero 
	  from canciones join ( select genero from cancionesYplaylist_usuario 
	  where usuario = 'camilom' and playlist = 'Prueba 2' 
	  group by genero) as A 
	  on canciones.genero = A.genero) as B except(select cancion,autor,genero from cancionesYplaylist_usuario 
	  where usuario = 'camilom' and playlist = 'Prueba 2');
										
										




-- DROPS --
drop table tiene cascade ;
drop table contiene;
drop table usuario;
drop table canciones;
drop table playlist;
drop table prueba;


select * 
from canciones;


delete from playlist 
where nombre = 'Papi';


select * 
from tiene join playlist
on tiene.id_playlist = playlist.id;										








