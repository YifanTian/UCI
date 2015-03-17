README.txt

IMPORTANT PLEASE READ THIS FILE BEFORE RUNNING THIS ECLIPSE PROGRAM

INSTRUCTIONS:

IN MYSQL:

WITH ROOT PRIVALEGES: start mysql -u root -p [if password is given] < path_to/credentials_04.sql
	//USERNAME: dbuser
	//PASSWORD: dbpassword
WITH CREDENTIALS start mysql -u dbuser -p -D moviedb < path_to/createtable_04.sql
				 start mysql -u dbuser -p -D moviedb < path_to/data.sql 
 



1. Import project into workspace for Eclipse
2. JAR file should be automatically added to the build path, if expand libs folder,
	right-click on JAR and click "Add To BuildPath".
3. Open Login.java and hit the Run Button.