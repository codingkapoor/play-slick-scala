# Play Slick Scala
Restful webservices in Scala using Play framework and Slick

## Setup Database

```
CREATE DATABASE slick;
USE slick;

CREATE TABLE `USER` (
	`ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`FIRST_NAME` TEXT NOT NULL,
	`LAST_NAME` TEXT NOT NULL,
	`MOBILE` BIGINT NOT NULL,
	`EMAIL` TEXT NOT NULL
);

CREATE TABLE `ADDRESS` (
	`ID` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`CITY` TEXT NOT NULL,
	`STATE` TEXT NOT NULL,
	`ZIP` BIGINT NOT NULL,
	`USER_ID` BIGINT NOT NULL,
	FOREIGN KEY (USER_ID) REFERENCES USER(ID)
);
```

## Start Service

```
sbt eclipse;
sbt clean compile run;
```

# Create User

```
curl -X POST \
  http://localhost:9000/users \
  -H 'content-type: application/json' \
  -d '{
	"firstName": "Shivam",
	"lastName": "Kapoor",
	"mobile": 9663006554,
	"email": "mail@shivamkapoor.com"
}'
```
