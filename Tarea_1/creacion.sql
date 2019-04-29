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
