CREATE 
VIEW `v_music_comment` AS
    select 
        `m_comment`.`id` AS `id`,
        `m_comment`.`content` AS `content`,
        `m_comment`.`upd_date` AS `createDate`,
        `v_user`.`id` AS `userBean.id`,
        `v_user`.`nickname` AS `userBean.nickname`,
        `v_music`.`id` AS `articleBean.id`,
        `v_music`.`title` AS `articleBean.title`,
        `v_music`.`description` AS `articleBean.description`,
        `v_music`.`userBean.id` AS `articleBean.userBean.id`,
        `v_music`.`userBean.nickname` AS `articleBean.userBean.nickname`
    from
        (`v_music`
        left join (`m_comment`
        left join `v_user` ON ((`v_user`.`id` = `m_comment`.`user_id`))) ON ((`v_music`.`id` = `m_comment`.`article_id`)))
    where
        (`m_comment`.`del_flg` = '0')
    order by `m_comment`.`id` desc;
