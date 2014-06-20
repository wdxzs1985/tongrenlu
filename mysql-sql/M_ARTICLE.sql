CREATE TABLE `M_ARTICLE` (
  `id` int(11) NOT NULL auto_increment,
  `user_id` int(11) default NULL,
  `title` varchar(100) default NULL,
  `description` text,
  `publish_flg` char(1) NOT NULL default '0',
  `publish_date` timestamp NULL default NULL,
  `recommend` char(1) NOT NULL default '0',
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
