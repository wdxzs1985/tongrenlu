CREATE TABLE `m_order_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `shop` varchar(45) DEFAULT NULL,
  `price` decimal(10,3) NOT NULL DEFAULT '0.000',
  `quantity` decimal(10,3) NOT NULL DEFAULT '0.000',
  `exchange_rate` decimal(10,3) NOT NULL DEFAULT '0.000',
  `fee` decimal(10,3) NOT NULL DEFAULT '0.000',
  `status` int(1) NOT NULL DEFAULT '0',
  `create_date` timestamp NULL DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `receive_date` timestamp NULL DEFAULT NULL,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;