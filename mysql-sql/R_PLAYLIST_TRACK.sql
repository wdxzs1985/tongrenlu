CREATE TABLE `R_PLAYLIST_TRACK` (
  `id` int(11) NOT NULL auto_increment,
  `playlist_id` int(11) default NULL,
  `file_id` varchar(45) default NULL,
  `order_no` int(11) default NULL,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
