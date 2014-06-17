CREATE TABLE `M_TRACK` (
  `id` int(11) NOT NULL auto_increment,
  `track` varchar(255) default NULL,
  `artist` varchar(255) default NULL,
  `instrumental` char(1) default '0',
  `original` text,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
