CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internet_shop`.`products` (
  `product_id` BIGINT(11) NOT NULL,
  `product_name` VARCHAR(225) NOT NULL,
  `product_price` DOUBLE NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE INDEX `products_id_UNIQUE` (`product_id` ASC) VISIBLE,
  UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `internet_shop`.`products`
ADD COLUMN `deleted` TINYINT NOT NULL DEFAULT 0 AFTER `product_price`;
ALTER TABLE `internet_shop`.`products`
CHANGE COLUMN `product_id` `product_id` BIGINT NOT NULL AUTO_INCREMENT ;

ALTER TABLE `internet_shop`.`products`
CHANGE COLUMN `product_name` `name` VARCHAR(225) NOT NULL ,
CHANGE COLUMN `product_price` `price` DOUBLE NULL DEFAULT NULL ,
DROP INDEX `product_name_UNIQUE` ;
#orders
CREATE TABLE `internet_shop`.`orders` (
  `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE,
  INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `orders_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts` (
  `shopping_carts_id` BIGINT(11) NOT NULL,
  `user_id` BIGINT(11) NULL,
  PRIMARY KEY (`shopping_carts_id`),
  INDEX `carts_users_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `carts_users_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`roles` (
  `role_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(225) NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `roles_id_UNIQUE` (`role_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

INSERT INTO `internet_shop`.`roles` (`role_name`) VALUES ('ADMIN');
INSERT INTO `internet_shop`.`roles` (`role_name`) VALUES ('USER');

CREATE TABLE `internet_shop`.`users_roles` (
  `users_id` BIGINT(11) NOT NULL,
  `roles_id` BIGINT(11) NOT NULL,
  INDEX `users_id_fk_idx` (`users_id` ASC) VISIBLE,
  INDEX `roles_id_fk_idx` (`roles_id` ASC) VISIBLE,
  CONSTRAINT `users_id_fk`
    FOREIGN KEY (`users_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `roles_id_fk`
    FOREIGN KEY (`roles_id`)
    REFERENCES `internet_shop`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`orders_products` (
  `orders_id` BIGINT(11) NOT NULL,
  `products_id` BIGINT(11) NOT NULL,
  INDEX `orders_id_fk_idx` (`orders_id` ASC) VISIBLE,
  INDEX `products_id_fk_idx` (`products_id` ASC) VISIBLE,
  CONSTRAINT `orders_id_fk`
    FOREIGN KEY (`orders_id`)
    REFERENCES `internet_shop`.`orders` (`order_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `products_id_fk`
    FOREIGN KEY (`products_id`)
    REFERENCES `internet_shop`.`products` (`product_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`shopping_carts` (
  `shopping_cart_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(11) NOT NULL,
  `deleted` TINYINT(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`shopping_cart_id`),
  UNIQUE INDEX `shopping_cart_id_UNIQUE` (`shopping_cart_id` ASC) VISIBLE,
  INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `users_id_fk`
    FOREIGN KEY (`user_id`)
    REFERENCES `internet_shop`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE `internet_shop`.`users` (
  `user_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(225) NOT NULL,
  `password` VARCHAR(225) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

ALTER TABLE `internet_shop`.`users`
ADD COLUMN `login` VARCHAR(225) NOT NULL AFTER `password`,
ADD COLUMN `deleted` TINYINT(1) NOT NULL DEFAULT 0 AFTER `login`,
CHANGE COLUMN `user_name` `user_name` VARCHAR(225) NULL ,
ADD UNIQUE INDEX `login_UNIQUE` (`login` ASC) VISIBLE,
DROP INDEX `user_name_UNIQUE` ;

ALTER TABLE `internet_shop`.`users_roles`
DROP FOREIGN KEY `roles_id_fk`,
DROP FOREIGN KEY `users_id_fk`;
ALTER TABLE `internet_shop`.`users_roles`
CHANGE COLUMN `users_id` `user_id` BIGINT NOT NULL ,
CHANGE COLUMN `roles_id` `role_id` BIGINT NOT NULL ;
ALTER TABLE `internet_shop`.`users_roles`
ADD CONSTRAINT `role_id_fk`
  FOREIGN KEY (`role_id`)
  REFERENCES `internet_shop`.`roles` (`role_id`),
ADD CONSTRAINT `user_id_fk`
  FOREIGN KEY (`user_id`)
  REFERENCES `internet_shop`.`users` (`user_id`);
