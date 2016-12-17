# play-slick-scala
Restful webservices in Scala using Play framework and Slick

# Setup Database
**Note:** Insert appropriate values after setting up database.

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

# Run
```
sbt eclipse;
sbt clean compile run;
```
