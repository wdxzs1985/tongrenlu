CREATE TABLE `R_COLLECT` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `like_id` int(11) default NULL,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
