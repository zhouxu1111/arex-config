CREATE DATABASE IF NOT EXISTS arexdb;

ALTER DATABASE arexdb
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;
CREATE USER 'arex_admin'@'localhost' IDENTIFIED BY 'arex_admin_password';
GRANT ALL PRIVILEGES ON arexdb.* TO 'arex_admin'@'localhost';
