CREATE TABLE `stock` (
   `stock_id` int NOT NULL AUTO_INCREMENT,
   `stock_name` varchar(200) NOT NULL,
   `stock_industry_id` int NOT NULL,
   PRIMARY KEY (`stock_id`),
   UNIQUE KEY `stock_name_UNIQUE` (`stock_name`),
   KEY `stock_industry_id` (`stock_industry_id`),
   CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`stock_industry_id`) REFERENCES `industry` (`industry_id`)
 ) ENGINE=InnoDB AUTO_INCREMENT=29084 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `industry` (
   `industry_id` int NOT NULL AUTO_INCREMENT,
   `industry_name` varchar(200) NOT NULL,
   PRIMARY KEY (`industry_id`),
   UNIQUE KEY `industry_name_UNIQUE` (`industry_name`)
 ) ENGINE=InnoDB AUTO_INCREMENT=30168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci


CREATE TABLE `stockmarket` (
   `stock_id` int NOT NULL,
   `market_price` float NOT NULL,
   `market_date` date NOT NULL,
   KEY `stock_id` (`stock_id`),
   CONSTRAINT `stockmarket_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci