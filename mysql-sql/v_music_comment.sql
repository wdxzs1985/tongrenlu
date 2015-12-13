CREATE 
VIEW `v_music_comment` AS
    select 
        `m_comment`.`id` AS `id`,
        `m_comment`.`Content` AS `content`,
        `m_comment`.`upd_date` AS `createDate`,
        `m_user`.`id` AS `userBean.id`,
        `m_user`.`nickname` AS `userBean.nickname`,
        `m_article`.`id` AS `articleBean.id`,
        `m_article`.`title` AS `articleBean.title`,
        `m_article`.`description` AS `articleBean.description`,
        `article_user`.`id` AS `articleBean.userBean.id`,
        `article_user`.`nickname` AS `articleBean.userBean.nickname`
    from
        ((((`m_comment`
        left join `m_user` ON ((`m_user`.`id` = `m_comment`.`user_id`)))
        left join `m_article` ON ((`m_article`.`id` = `m_comment`.`article_id`)))
        left join `r_music` ON ((`r_music`.`id` = `m_comment`.`article_id`)))
        left join `m_user` `article_user` ON ((`article_user`.`id` = `m_article`.`user_id`)))
    where
        ((`m_comment`.`del_flg` = '0')
            and (`r_music`.`id` is not null))
    order by `m_comment`.`id` desc