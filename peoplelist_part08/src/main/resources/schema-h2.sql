create table person (
    id  BIGINT UNSIGNED NOT NULL AUTO_INCREMENT ,
	firstName VARCHAR(20) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	gender VARCHAR(20),
    age INT,
	job VARCHAR(50),
	avatar BLOB,
	CONSTRAINT pk_id PRIMARY KEY(id)
);