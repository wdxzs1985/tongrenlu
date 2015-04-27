CREATE 
VIEW `v_timeline` AS
    select 
        `v_music`.`id` AS `articleBean.id`,
        `v_music`.`title` AS `articleBean.title`,
        NULL AS `userBean.id`,
        NULL AS `userBean.nickname`,
        `v_music`.`description` AS `content`,
        `v_music`.`publishDate` AS `createDate`,
        NULL AS `followId`,
        NULL AS `followerId`,
        NULL AS `redFlg`,
        NULL AS `translateFlg`,
        'publish_music' AS `action`
    from
        `v_music`
    where
        (`v_music`.`publishFlg` in ('1','2'))
    union select 
        `v_music_comment`.`articleBean.id` AS `articleBean.id`,
        `v_music_comment`.`articleBean.title` AS `articleBean.title`,
        `v_music_comment`.`userBean.id` AS `userBean.id`,
        `v_music_comment`.`userBean.nickname` AS `userBean.nickname`,
        `v_music_comment`.`content` AS `content`,
        `v_music_comment`.`createDate` AS `createDate`,
        `v_music_comment`.`userBean.id` AS `followId`,
        `v_music_comment`.`articleBean.userBean.id` AS `followerId`,
        NULL AS `redFlg`,
        NULL AS `translateFlg`,
        'comment_music' AS `action`
    from
        `v_music_comment`