create table service (
    idservice int primary key,
    nom varchar(64)
);

create table personnel (
    idpersonnel int primary key,
    nom varchar(64),
    prenom varchar(64),
    tel varchar(10),
    mail varchar(64),
    
    service int,
    foreign key(service) references service(idservice)
);

create table motif (
    idmotif int primary key,
    libelle varchar(64)
);

create table absence (
    datedebut date primary key,
    datefin date,
    
    personnel int,
    motif int,
    foreign key(personnel) references personnel(idpersonnel),
    foreign key(motif) references motif(idmotif)
);

create table responsable (
    login varchar(64) primary key,
    motdepasse varchar(64)
);

-- Création utilisateur BDD --

create user "dbuser"@"localhost"
identified by "motdepasse";

grant select, insert, update, delete
on mediatek86.*
to "dbuser"@"localhost";
