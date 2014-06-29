CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `root`@`localhost` 
    SQL SECURITY DEFINER
VIEW `v_comic_comment` AS
    select 
        `m_comment`.`id` AS `id`,
        `m_comment`.`content` AS `content`,
        `m_comment`.`upd_date` AS `createDate`,
        `v_user`.`id` AS `userBean.id`,
        `v_user`.`nickname` AS `userBean.nickname`,
        `v_comic`.`id` AS `articleBean.id`,
        `v_comic`.`title` AS `articleBean.title`,
        `v_comic`.`description` AS `articleBean.description`,
        `v_comic`.`userBean.id` AS `articleBean.userBean.id`,
        `v_comic`.`userBean.nickname` AS `articleBean.userBean.nickname`,
        `v_comic`.`redFlg` AS `redFlg`,
        `v_comic`.`translateFlg` AS `translateFlg`
    from
        (`v_comic`
        left join (`m_comment`
        left join `v_user` ON ((`v_user`.`id` = `m_comment`.`user_id`))) ON ((`v_comic`.`id` = `m_comment`.`article_id`)))
    where
        (`m_comment`.`del_flg` = '0')
    order by `m_comment`.`id` desc;
