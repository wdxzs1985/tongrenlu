CREATE TABLE `m_article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` text,
  `publish_flg` char(1) NOT NULL DEFAULT '0',
  `publish_date` timestamp NULL DEFAULT NULL,
  `access_cnt` int(11) NOT NULL DEFAULT '0',
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2995 DEFAULT CHARSET=utf8;