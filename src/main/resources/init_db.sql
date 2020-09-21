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
