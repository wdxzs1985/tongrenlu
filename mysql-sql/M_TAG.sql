CREATE TABLE `M_TAG` (
  `id` int(11) NOT NULL auto_increment,
  `tag` varchar(255) default NULL,
  `type` char(1) NOT NULL default '0',
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
