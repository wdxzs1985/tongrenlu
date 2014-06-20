CREATE TABLE `M_USER` (
  `id` int(11) NOT NULL auto_increment,
  `nickname` varchar(20) default NULL,
  `signature` varchar(200) default NULL,
  `email` varchar(200) default NULL,
  `password` varchar(32) default NULL,
  `fingerprint` varchar(32) default NULL,
  `role` char(1) NOT NULL default '0',
  `include_red_flg` char(1) NOT NULL default '0',
  `only_translate_flg` char(1) NOT NULL default '0',
  `only_vocal_flg` char(1) NOT NULL default '0',
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
