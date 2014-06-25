CREATE TABLE `R_COMIC` (
  `id` int(11) NOT NULL,
  `red_flg` char(1) NOT NULL default '0',
  `translate_flg` char(1) NOT NULL default '0',
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
