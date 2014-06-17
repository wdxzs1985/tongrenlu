CREATE TABLE `M_ARTICLE_COMMENT` (
  `id` int(11) NOT NULL auto_increment,
  `sender_user_id` int(11) default NULL,
  `article_id` int(11) default NULL,
  `Content` text,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
