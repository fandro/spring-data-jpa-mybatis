
CREATE TABLE cutomer
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  age INT(11) NOT NULL,
  birth DATE,
  createdTime DATETIME(6),
  email VARCHAR(255),
  last_name VARCHAR(50) NOT NULL
);
CREATE TABLE department
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  dept_name VARCHAR(255),
  mgr_id INT(11)
);
CREATE INDEX FKkuooo9cf7dqsf5pbqf9doyjal ON department (mgr_id);
CREATE TABLE hibernate_sequence
(
  next_val BIGINT(20)
);
CREATE TABLE item
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  item_name VARCHAR(255)
);
CREATE TABLE manager
(
  id INT(11) PRIMARY KEY NOT NULL,
  mgr_name VARCHAR(255)
);
CREATE TABLE orders
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  order_name VARCHAR(255),
  customer_id INT(11)
);
CREATE INDEX FKjeule0j7cca6pihcdpkq8bk23 ON orders (customer_id);
CREATE TABLE category
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(255),
  created_by_user VARCHAR(255) NOT NULL,
  creation_time DATETIME(6) NOT NULL,
  modified_by_user VARCHAR(255) NOT NULL,
  modify_action_time DATETIME(6)
);
CREATE TABLE item_category
(
  id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  item_id INT(11) NOT NULL,
  category_id INT(11) NOT NULL
);
