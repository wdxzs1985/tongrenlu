CREATE TABLE `M_ACCESS` (
  `id` int(11) NOT NULL auto_increment,
  `article_id` int(11) default NULL,
  `user_id` int(11) default NULL,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
