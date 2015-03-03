CREATE TABLE `m_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `amount_jp` decimal(10,3) NOT NULL DEFAULT '0.000',
  `amount_cn` decimal(10,3) NOT NULL DEFAULT '0.000',
  `fee` decimal(10,3) NOT NULL DEFAULT '0.000',
  `total` decimal(10,3) NOT NULL DEFAULT '0.000',
  `status` int(1) NOT NULL DEFAULT '0',
  `create_date` timestamp NULL DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT NULL,
  `pay_date` timestamp NULL DEFAULT NULL,
  `send_date` timestamp NULL DEFAULT NULL,
  `receive_date` timestamp NULL DEFAULT NULL,
  `cancel_date` timestamp NULL DEFAULT NULL,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
