CREATE TABLE `m_order_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `title` varchar(45) NOT NULL,
  `amount` decimal(10,3) NOT NULL,
  `pay_link` varchar(255) DEFAULT NULL,
  `pay_no` varchar(45) DEFAULT NULL,
  `create_date` timestamp NULL DEFAULT NULL,
  `pay_date` timestamp NULL DEFAULT NULL,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
