CREATE TABLE `m_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT NULL,
  `signature` varchar(80) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `fingerprint` varchar(32) DEFAULT NULL,
  `role` char(1) NOT NULL DEFAULT '0',
  `include_red_flg` char(1) NOT NULL DEFAULT '0',
  `only_translate_flg` char(1) NOT NULL DEFAULT '0',
  `only_vocal_flg` char(1) NOT NULL DEFAULT '0',
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
