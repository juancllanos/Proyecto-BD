--- CREACION ---

create table usuario
( nickname varchar(15),
 email varchar(30),
 nombre varchar(20),
 contraseña varchar(15),
 primary key(nickname)
);

create table canciones
( id varchar (7),
 nombre varchar(20),
 autor varchar(20),
 genero varchar(15),
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

create table gusta 
( usuario varchar (15),
 id_cancion varchar(7),
  primary key(usuario,id_cancion),
  foreign key (usuario) references usuario(nickname),
 foreign key (id_cancion) references canciones(id)
);

create table contiene
( id_playlist int,
 id_cancion varchar(7),
  primary key(id_cancion,id_playlist),
  foreign key (id_cancion) references canciones(id),
 foreign key (id_playlist) references playlist(id)
);


-- INSERCION --

insert into usuario values('santiagom','Santiago','123');
insert into usuario values('jcllanos', 'Juan Camilo', '321');
insert into usuario values('camilom', 'Camilo', '213');

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

insert into playlist(nombre) values('Rock & Metal');
insert into playlist(nombre)  values('Rap Real');
insert into playlist(nombre)  values('Clasica por gusto');
insert into playlist(nombre)  values('Perreo');
insert into playlist(nombre)  values('Preferido');


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

insert into tiene values ('jcllanos',2);
insert into tiene values ('jcllanos',4);

insert into tiene values ('santiagom',1);
insert into tiene values ('santiagom',3);

insert into tiene values ('camilom',1);
insert into tiene values ('camilom',5);



-- LECTURAS --

select*
from playlist
where nombre = 'Hola';

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

col_length



--- FUNCIONES ---

-- 1) Crear un playlist 
insert into playlists(nombre) values( nombre_ingresado_usuario )
insert into tiene values(id_usuario,id_playlist)


-- 2) Buscar playlist o canciones -- 
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
order by usuario,playlist
;


/* como aca por ejemplo*/
select *
from cancionesYplaylist_usuario;

drop view cancionesYplaylist_usuario;

-- 3) Añadir cancion a un playlist 

-- 4) Eliminar cancion de un playlist 
delete from contiene
where id_canciones = 

select *
from contiene join canciones
on contiene.id_cancion = canciones.id;

-- 5) Recomendaciones 




-- DROPS --
drop table tiene;
drop table contiene;
drop table gusta;
drop table usuario;
drop table canciones;
drop table playlist;




























