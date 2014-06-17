CREATE TABLE `M_USER` (
  `id` int(11) NOT NULL auto_increment,
  `nickname` varchar(20) default NULL,
  `email` varchar(200) default NULL,
  `password` varchar(32) default NULL,
  `signature` varchar(200) default NULL,
  `admin_flg` char(1) NOT NULL default '0',
  `red_flg` char(1) NOT NULL default '0',
  `translate_flg` char(1) NOT NULL default '0',
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
