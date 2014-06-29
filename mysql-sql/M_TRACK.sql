CREATE TABLE `m_track` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `artist` varchar(255) DEFAULT NULL,
  `instrumental` char(1) DEFAULT '0',
  `original` text,
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
