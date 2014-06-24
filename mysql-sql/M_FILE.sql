CREATE TABLE `M_FILE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) default NULL,
  `name` varchar(255) default NULL,
  `extension` varchar(20) default NULL,
  `content_type` varchar(20) default NULL,
  `order_no` int(11) default 0,
  `upd_date` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `del_flg` char(1) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
