create table usuario
( nickname varchar(15),
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
( id varchar (7),
 nombre varchar(30),
  primary key(id)
 );


create table tiene
( usuario varchar (15),
 id_playlist varchar(7),
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
( id_playlist varchar (7),
 id_cancion varchar(7),
  primary key(id_cancion,id_playlist),
  foreign key (id_cancion) references canciones(id),
 foreign key (id_playlist) references playlist(id)
);

	
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

insert into playlist values('0010','Rock & Metal');
insert into playlist values('0020', 'Rap Real');
insert into playlist values('0030', 'Clasica por gusto');
insert into playlist values('0050', 'Perreo');
insert into playlist values('0060', 'Preferido');


insert into contiene values ('0010','009');
insert into contiene values ('0010','000');
insert into contiene values ('0020','001');
insert into contiene values ('0020','002');
insert into contiene values ('0030','003');
insert into contiene values ('0030','004');
insert into contiene values ('0050','005');
insert into contiene values ('0050','006');
insert into contiene values ('0060','007');
insert into contiene values ('0060','008');

insert into tiene values ('jcllanos','0020');
insert into tiene values ('jcllanos','0050');

insert into tiene values ('santiagom','0010');
insert into tiene values ('santiagom','0030');

insert into tiene values ('camilom','0010');
insert into tiene values ('camilom','0060');




select * 
from contiene join 
(select usuario,id_playlist
from tiene join usuario 
on tiene.usuario = usuario.nickname) as A
on contiene.id_playlist = A.id_playlist
;



/*
insert into gusta values ('','');
insert into gusta values ('','');
*/

drop table usuario;
drop table canciones;
drop table playlist;
drop table tiene;
drop table contiene;
drop table gusta;


























