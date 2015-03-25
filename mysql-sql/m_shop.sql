CREATE TABLE `m_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `shop_name` varchar(45) NOT NULL,
  `tax_rate` decimal(10,3) NOT NULL DEFAULT '0.000',
  `exchange_rate` decimal(10,3) NOT NULL DEFAULT '0.000',
  `fee_mailorder` decimal(10,3) NOT NULL DEFAULT '0.000',
  `fee_event` decimal(10,3) NOT NULL DEFAULT '0.000',
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
