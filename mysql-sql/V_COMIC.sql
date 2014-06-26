CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `tongrenlu`@`%` 
    SQL SECURITY DEFINER
VIEW `v_comic` AS
    select 
        `a`.`id` AS `id`,
        `a`.`title` AS `title`,
        `a`.`description` AS `description`,
        `a`.`publish_flg` AS `publishFlg`,
        `a`.`publish_date` AS `publishDate`,
        `a`.`recommend_flg` AS `recommendFlg`,
        `c`.`red_flg` AS `redFlg`,
        `c`.`translate_flg` AS `translateFlg`,
        `u`.`id` AS `userBean.id`,
        `u`.`nickname` AS `userBean.nickname`,
        `u`.`signature` AS `userBean.signature`,
        ifnull(`ac`.`cnt`, 0) AS `accessCount`,
        ifnull(`lc`.`cnt`, 0) AS `likeCount`
    from
        ((((`r_comic` `c`
        left join `m_article` `a` ON ((`a`.`id` = `c`.`id`)))
        left join `m_user` `u` ON ((`a`.`user_id` = `u`.`id`)))
        left join `v_article_access_count` `ac` ON ((`c`.`id` = `ac`.`articleId`)))
        left join `v_comic_like_count` `lc` ON ((`c`.`id` = `lc`.`articleId`)))
    where
        (`a`.`del_flg` = '0')