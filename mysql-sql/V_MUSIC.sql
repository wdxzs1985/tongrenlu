CREATE 
    ALGORITHM = UNDEFINED 
    DEFINER = `tongrenlu`@`%` 
    SQL SECURITY DEFINER
VIEW `v_music` AS
    select 
        `a`.`id` AS `id`,
        `a`.`title` AS `title`,
        `a`.`description` AS `description`,
        `a`.`publish_flg` AS `publishFlg`,
        `a`.`publish_date` AS `publishDate`,
        `a`.`recommend_flg` AS `recommendFlg`,
        `u`.`id` AS `userBean.id`,
        `u`.`nickname` AS `userBean.nickname`,
        `u`.`signature` AS `userBean.signature`,
        ifnull(`ac`.`cnt`, 0) AS `accessCount`,
        ifnull(`lc`.`cnt`, 0) AS `likeCount`,
        ifnull(`cc`.`cnt`, 0) AS `commentCount`
    from
        (((((`r_music` `m`
        left join `m_article` `a` ON ((`a`.`id` = `m`.`id`)))
        left join `m_user` `u` ON ((`a`.`user_id` = `u`.`id`)))
        left join `v_article_access_count` `ac` ON ((`m`.`id` = `ac`.`articleId`)))
        left join `v_article_comment_count` `cc` ON ((`m`.`id` = `cc`.`articleId`)))
        left join `v_music_like_count` `lc` ON ((`m`.`id` = `lc`.`articleId`)))
    where
        (`a`.`del_flg` = '0');
