-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema my_budget_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema my_budget_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `my_budget_db` DEFAULT CHARACTER SET utf8 ;
USE `my_budget_db` ;

-- -----------------------------------------------------
-- Table `my_budget_db`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `my_budget_db`.`account` (
  `account_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(40) NOT NULL,
  `currency` VARCHAR(40) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`account_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `my_budget_db`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `my_budget_db`.`transaction` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(100) NOT NULL,
  `amount` DECIMAL(8,2) NOT NULL,
  `account_id` INT NOT NULL,
  PRIMARY KEY (`transaction_id`, `account_id`),
  INDEX `fk_transaction_account_idx` (`account_id` ASC) VISIBLE,
  CONSTRAINT `fk_transaction_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `my_budget_db`.`account` (`account_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;




SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
