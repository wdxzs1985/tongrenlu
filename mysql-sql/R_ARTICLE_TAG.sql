CREATE TABLE `R_ARTICLE_TAG` (
  `id` int(11) NOT NULL auto_increment,
  `article_id` int(11) default NULL,
  `tag_id` int(11) default NULL,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
