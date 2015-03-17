CREATE USER 'dbuser'@'localhost' IDENTIFIED BY 'dbpassword';

GRANT ALL PRIVILEGES ON *.* TO 'dbuser'@'localhost' WITH GRANT OPTION;

CREATE USER 'dbuser'@'%' IDENTIFIED BY 'dbpassword';

GRANT ALL PRIVILEGES ON *.* TO 'dbuser'@'%' WITH GRANT OPTION;