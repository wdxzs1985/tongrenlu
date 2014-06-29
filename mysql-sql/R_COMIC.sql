CREATE TABLE `r_comic` (
  `id` int(11) NOT NULL,
  `red_flg` char(1) NOT NULL DEFAULT '0',
  `translate_flg` char(1) NOT NULL DEFAULT '0',
  `upd_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
